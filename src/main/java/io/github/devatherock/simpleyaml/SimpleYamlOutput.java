package io.github.devatherock.simpleyaml;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Class that converts an object into a simple <em>block</em> yaml
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleYamlOutput {
	/**
	 * Regular expression to identify a numeric field
	 */
	private static final Pattern PTRN_NUMERIC_VALUE = Pattern.compile("^[0-9]+$");

	/**
	 * Default instance
	 */
	private static final SimpleYamlOutput DEFAULT_INSTANCE = new SimpleYamlOutput();

	/**
	 * The indent size
	 */
	@Builder.Default
	private int indentSize = 2;

	/**
	 * List of fields with numeric values for which to quote the value
	 */
	@Builder.Default
	private List<String> numericFieldsToQuote = new ArrayList<>();

	/**
	 * Indicates whether to indent array values
	 */
	@Builder.Default
	private boolean indentArrays = true;

	/**
	 * The type of quote to use when quoting numeric fields
	 */
	@Builder.Default
	private QuoteType quoteType = QuoteType.DOUBLE;

	/**
	 * List of field name for which to use arrays with square brackets
	 */
	@Builder.Default
	private List<String> flowStyleArrayFields = new ArrayList<>();

	/**
	 * Converts the given object into a yaml String
	 *
	 * @param object the input object to convert to yaml
	 * @return the yaml representation of the input object
	 */
	public static String toYaml(Map<Object, Object> object) {
		return DEFAULT_INSTANCE.dump(object);
	}

	/**
	 * Converts the given object into a yaml String
	 *
	 * @param object the input object to convert to yaml
	 * @return the yaml representation of the input object
	 */
	public String dump(Map<Object, Object> object) {
		StringBuilder yamlContent = new StringBuilder();
		convertToYaml(object, yamlContent, 0);

		// Remove redundant newline characters at the end
		while (Character.isWhitespace(yamlContent.charAt(yamlContent.length() - 1))) {
			yamlContent.delete(yamlContent.length() - 1, yamlContent.length());
		}

		return yamlContent.toString();
	}

	/**
	 * Converts a {@link Map} into yaml with the specified indent and appends it to
	 * the supplied {@code builder}
	 *
	 * @param map
	 * @param builder
	 * @param indent
	 */
	private void convertToYaml(Map<Object, Object> map, StringBuilder builder, int indent) {
		map.forEach((key, value) -> {
			applyIndent(builder, indent);
			builder.append(key);
			builder.append(':');

			// To write arrays with square brackets
			if (flowStyleArrayFields.contains(key) && value instanceof List
					&& !(((List<Object>) value).stream().anyMatch(element -> element instanceof Map))) {
				builder.append(" [ ");
				((List<Object>) value).forEach(listElement -> {
					builder.append(listElement);
					builder.append(", ");
				});
				builder.replace(builder.length() - 2, builder.length(), " ]");
				builder.append(System.lineSeparator());
			} else if (numericFieldsToQuote.contains(key)
					&& ((value instanceof String && PTRN_NUMERIC_VALUE.matcher((String) value).matches())
							|| value instanceof Number)) {
				builder.append(' ');
				builder.append(quoteType.value);
				builder.append(value);
				builder.append(quoteType.value);
				builder.append(System.lineSeparator());
			} else {
				processValue(value, builder, indent);
			}
		});
	}

	/**
	 * Converts a field value into yaml and appends it to {@code builder}
	 *
	 * @param value   the value to convert
	 * @param builder the {@link StringBuilder} that contains the whole yaml
	 * @param indent  the indent to use for the value
	 */
	private void processValue(Object value, StringBuilder builder, int indent) {
		if (value instanceof Map) { // If value is a map
			builder.append(System.lineSeparator());
			convertToYaml((Map<Object, Object>) value, builder, indent + indentSize);
		} else if (value instanceof Collection) { // If value is an array, List, Set, etc
			builder.append(System.lineSeparator());
			for (Object element : (Collection<?>) value) {
				int arrayIndent = indent;
				if (indentArrays) {
					arrayIndent += indentSize;
				}
				applyIndent(builder, arrayIndent);
				builder.append('-');

				if (element instanceof Map) {
					builder.append(' ');

					Map<Object, Object> elementMap = (Map<Object, Object>) element;
					Map.Entry<Object, Object> firstEntry = elementMap.entrySet().iterator().next();
					builder.append(firstEntry.getKey());
					builder.append(':');

					int valueIndent = indent + indentSize;
					if (indentArrays) {
						valueIndent += indentSize;
					}
					processValue(firstEntry.getValue(), builder, valueIndent);
					elementMap.remove(firstEntry.getKey());
					convertToYaml(elementMap, builder, valueIndent);
				} else {
					processValue(element, builder, 0);
				}
			}
		} else { // If value is String, Boolean, Number, etc
			builder.append(' ');

			if (value instanceof String) {
				char currentChar;
				String valueString = (String) value;

				if (valueString.contains("\n")) {
					builder.append("|-");
					builder.append(System.lineSeparator());
					indent += indentSize;
					applyIndent(builder, indent);

					for (int index = 0; index < valueString.length(); index++) {
						currentChar = valueString.charAt(index);

						if (currentChar == '\n') {
							builder.append(System.lineSeparator());
							applyIndent(builder, indent);
						} else {
							builder.append(currentChar);
						}
					}
				} else {
					builder.append(value);
				}
			} else {
				builder.append(value);
			}

			builder.append(System.lineSeparator());
		}
	}

	/**
	 * Adds indent by appending the specified number of spaces
	 *
	 * @param builder
	 * @param indent
	 */
	private void applyIndent(StringBuilder builder, int indent) {
		for (int index = 0; index < indent; index++) {
			builder.append(' ');
		}
	}

	/**
	 * Enumeration for types of quotes to use
	 */
	public enum QuoteType {
		SINGLE("'"), DOUBLE("\"");

		String value;

		private QuoteType(String value) {
			this.value = value;
		}
	}
}