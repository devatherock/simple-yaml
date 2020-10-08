package io.github.devatherock.simpleyaml

/**
 * Class that converts an object into a simple <em>block</em> yaml
 */
class SimpleYamlOutput {
    int indentSize = 2
    def numericFieldsToQuote = []
    boolean indentArrays = true
    QuoteType quoteType = QuoteType.DOUBLE
    /**
     * List of field name for which to use arrays with square brackets
     */
    def flowStyleArrayFields = []

    /**
     * Converts the given object into a yaml String
     *
     * @param object
     * @return
     */
    String dump(Object object) {
        StringBuilder yamlContent = new StringBuilder()
        convertToYaml(object, yamlContent, 0)

        // Remove redundant newline characters at the end
        while (Character.isWhitespace(yamlContent.charAt(yamlContent.length() - 1))) {
            yamlContent.delete(yamlContent.length() - 1, yamlContent.length())
        }

        return yamlContent.toString()
    }

    /**
     * Converts a {@link Map} into yaml with the specified indent and appends it to the supplied {@code builder}
     *
     * @param map
     * @param builder
     * @param indent
     */
    void convertToYaml(Map map, StringBuilder builder, int indent) {
        map.each { key, value ->
            applyIndent(builder, indent)
            builder.append(key)
            builder.append(':')

            // To write arrays with square brackets
            if (flowStyleArrayFields.contains(key) && value instanceof List && !value.any { it instanceof Map }) {
                builder.append(' [ ')
                value.each { listElement ->
                    builder.append(listElement)
                    builder.append(', ')
                }
                builder.replace(builder.length() - 2, builder.length(), ' ]')
                builder.append(System.lineSeparator())
            } else if (numericFieldsToQuote.contains(key) && ((value instanceof String && value =~ '^[0-9]+$') ||
                    value instanceof Number)) {
                builder.append(' ')
                builder.append(quoteType.value)
                builder.append(value)
                builder.append(quoteType.value)
                builder.append(System.lineSeparator())
            } else {
                processValue(value, builder, indent)
            }
        }
    }

    /**
     * Converts a field value into yaml and appends it to {@code builder}
     *
     * @param value the value to convert
     * @param builder the {@link StringBuilder} that contains the whole yaml
     * @param indent the indent to use for the value
     */
    void processValue(def value, StringBuilder builder, int indent) {
        if (value instanceof Map) { // If value is a map
            builder.append(System.lineSeparator())
            convertToYaml(value, builder, indent + indentSize)
        } else if (value instanceof Collection) { // If value is an array, List, Set, etc
            builder.append(System.lineSeparator())
            value.each { element ->
                int arrayIndent = indent
                if (indentArrays) {
                    arrayIndent += indentSize
                }
                applyIndent(builder, arrayIndent)
                builder.append('-')

                if (element instanceof Map) {
                    builder.append(' ')

                    def firstEntry = element.entrySet().first()
                    builder.append(firstEntry.key)
                    builder.append(':')

                    int valueIndent = indent + indentSize
                    if (indentArrays) {
                        valueIndent += indentSize
                    }
                    processValue(firstEntry.value, builder, valueIndent)
                    element.remove(firstEntry.key)
                    convertToYaml(element, builder, valueIndent)
                } else {
                    processValue(element, builder, 0)
                }
            }
        } else { // If value is String, Boolean, Number, etc
            builder.append(' ')

            if (value instanceof String) {
                char currentChar;

                if (value.contains('\n')) {
                    builder.append('|-')
                    builder.append(System.lineSeparator())
                    indent += indentSize
                    applyIndent(builder, indent)

                    for (int index = 0; index < value.length(); index++) {
                        currentChar = value.charAt(index)

                        if (currentChar == '\n') {
                            builder.append(System.lineSeparator())
                            applyIndent(builder, indent)
                        } else {
                            builder.append(currentChar)
                        }
                    }
                } else {
                    builder.append(value)
                }
            } else {
                builder.append(value)
            }

            builder.append(System.lineSeparator())
        }
    }

    /**
     * Adds indent by appending the specified number of spaces
     *
     * @param builder
     * @param indent
     */
    void applyIndent(StringBuilder builder, int indent) {
        for (int index = 0; index < indent; index++) {
            builder.append(' ')
        }
    }

    /**
     * Enumeration for types of quotes to use
     */
    enum QuoteType {
        SINGLE('\''), DOUBLE('"')

        String value

        private QuoteType(String value) {
            this.value = value
        }
    }
}