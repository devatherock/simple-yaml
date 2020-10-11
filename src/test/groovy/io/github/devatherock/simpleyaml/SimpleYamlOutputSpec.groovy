package io.github.devatherock.simpleyaml

import spock.lang.Specification

/**
 * Test class for {@link SimpleYamlOutput}
 */
class SimpleYamlOutputSpec extends Specification {

    def 'test dump - default settings'() {
        given:
        String expectedOutput = SimpleYamlOutputSpec.class.classLoader.getResourceAsStream(expectedOutputFile).text

        when:
        String output = new SimpleYamlOutput().dump(input)

        then:
        output == expectedOutput

        where:
        input << [
                [
                        'foo'  : 'bar',
                        'hello': 'world'
                ],
                [
                        'foo'   : 'bar',
                        'colors': ['red', 'blue']
                ],
                [
                        'foo'          : 'bar',
                        'logging.level': [
                                'io.github.devatherock'           : 'INFO',
                                'io.github.devatherock.simpleyaml': 'WARN'
                        ]
                ],
                [
                        'foo'   : 'bar',
                        'colors': [
                                [
                                        'name': 'red',
                                        'code': 'ff0000'
                                ],
                                [
                                        'name': 'blue',
                                        'code': '0000ff'
                                ]
                        ]
                ],
                [
                        'foo' : 'bar',
                        'text': 'The quick brown fox' + System.lineSeparator() + 'jumped over a lazy dog'
                ],
                [
                        'foo'  : 'bar',
                        'count': 1
                ]
        ]
        expectedOutputFile << [
                'string-values.yml',
                'list-value.yml',
                'map-value.yml',
                'list-of-maps.yml',
                'new-line.yml',
                'numeric-value.yml'
        ]
    }

    def 'test dump - quote numeric fields'() {
        given:
        String expectedOutput = SimpleYamlOutputSpec.class.classLoader.getResourceAsStream('quoted-numeric-value.yml').text

        when:
        String output = new SimpleYamlOutput(numericFieldsToQuote: ['version']).dump(input)

        then:
        output == expectedOutput

        where:
        input << [
                [
                        'foo'    : 'bar',
                        'version': '1'
                ],
                [
                        'foo'    : 'bar',
                        'version': 1
                ]
        ]
    }

    def 'test dump - quote non-numeric field'() {
        given:
        String expectedOutput = SimpleYamlOutputSpec.class.classLoader.getResourceAsStream(expectedOutputFile).text

        when:
        String output = new SimpleYamlOutput(numericFieldsToQuote: ['version']).dump(input)

        then:
        output == expectedOutput

        where:
        input << [
                [
                        'foo'    : 'bar',
                        'version': '1.2.2'
                ],
                [
                        'foo'    : 'bar',
                        'version': false
                ]
        ]
        expectedOutputFile << [
                'quote-non-numeric-value.yml',
                'quote-boolean-value.yml'
        ]
    }

    def 'test dump - flow style array fields'() {
        given:
        String expectedOutput = SimpleYamlOutputSpec.class.classLoader.getResourceAsStream('flow-list-value.yml').text

        when:
        String output = new SimpleYamlOutput(flowStyleArrayFields: ['colors']).dump([
                'foo'   : 'bar',
                'colors': ['red', 'blue']
        ])

        then:
        output == expectedOutput
    }

    def 'test dump - unindented array values'() {
        given:
        String expectedOutput = SimpleYamlOutputSpec.class.classLoader.getResourceAsStream('unindented-list-value.yml').text

        when:
        String output = new SimpleYamlOutput(indentArrays: false).dump([
                'foo'   : 'bar',
                'colors': ['red', 'blue']
        ])

        then:
        output == expectedOutput
    }

    def 'test dump - unindented object array values'() {
        given:
        String expectedOutput = SimpleYamlOutputSpec.class.classLoader.getResourceAsStream('unindented-list-of-maps.yml').text

        when:
        String output = new SimpleYamlOutput(indentArrays: false, flowStyleArrayFields: ['foo', 'colors']).dump([

                'foo'   : 'bar',
                'colors': [
                        [
                                'name': 'red',
                                'code': 'ff0000'
                        ],
                        [
                                'name': 'blue',
                                'code': '0000ff'
                        ]
                ]

        ])

        then:
        output == expectedOutput
    }
}