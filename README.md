[![CircleCI](https://img.shields.io/circleci/project/github/devatherock/simple-yaml/master.svg)](https://circleci.com/gh/devatherock/simple-yaml)
[![Download](https://api.bintray.com/packages/devatherock/simple-yaml/simple-yaml/images/download.svg) ](https://bintray.com/devatherock/simple-yaml/simple-yaml/_latestVersion)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=simple-yaml&metric=ncloc)](https://sonarcloud.io/dashboard?id=simple-yaml)
[![Coverage Status](https://coveralls.io/repos/github/devatherock/simple-yaml/badge.svg?branch=master)](https://coveralls.io/github/devatherock/simple-yaml?branch=master)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=simple-yaml&metric=alert_status)](https://sonarcloud.io/component_measures?id=simple-yaml&metric=alert_status&view=list)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
# simple-yaml
A simple YAML generator for Java

## Usage

For Gradle:
```groovy
dependencies {
    implementation group: 'io.github.devatherock', name: 'simple-yaml', version: '0.2.0'
}
```

For Maven:
```xml
<dependencies>
    <dependency>
        <groupId>io.github.devatherock</groupId>
        <artifactId>simple-yaml</artifactId>
        <version>0.2.0</version>
    </dependency>
</dependencies>
```

### Generating YAML
#### With default settings
```java
Map<String, Object> map = new HashMap<>();
map.put("foo", "bar");
map.put("version", "1");
map.put("colors", Arrays.asList("red", "blue"));
String output = SimpleYamlOutput.toYaml(map);
```

Output:
```yaml
foo: bar
version: 1
colors:
  - red
  - blue
```

#### With custom settings
```java
Map<String, Object> map = new HashMap<>();
map.put("foo", "bar");
map.put("version", "1");
map.put("colors", Arrays.asList("red", "blue"));
SimpleYamlOutput yaml = SimpleYamlOutput.builder()
        .numericFieldToQuote("version")
        .flowStyleArrayField("colors")
        .indentArrays(false)
        .indentSize(3)
        .quoteType(SimpleYamlOutput.QuoteType.SINGLE)
        .build();
String output = yaml.dump(map);
```

Output:
```yaml
foo: bar
version: '1'
colors: [ red, blue ]
```
