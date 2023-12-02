# Changelog

## [Unreleased]
### Added
- Spotless plugin

### Changed
- Configure Renovate
- feat: Updated changelog updater user
- fix(deps): update dependency org.projectlombok:lombok to v1.18.26
- chore(deps): update plugin org.sonarqube to v4
- chore(deps): update plugin com.github.kt3k.coveralls to v2.12.2
- chore(deps): update plugin io.github.gradle-nexus.publish-plugin to v1.3.0
- chore(deps): update plugin org.owasp.dependencycheck to v8.2.1
- Upgraded to gradle 7 and Java 17
- fix(deps): update dependency org.spockframework:spock-core to v2.3-groovy-4.0
- fix(deps): update dependency org.projectlombok:lombok to v1.18.28
- chore(deps): update plugin com.diffplug.spotless to v6.19.0
- chore(deps): update plugin org.sonarqube to v4.1.0.3113
- chore(deps): update plugin org.sonarqube to v4.2.0.3129
- chore(deps): update plugin org.sonarqube to v4.2.1.3168
- chore(deps): update devatherock/simple-slack docker tag to v1
- chore(deps): update plugin org.owasp.dependencycheck to v8.3.1
- Configure Mend Bolt for GitHub
- chore(deps): update dependency gradle to v7.6.2
- chore(deps): update plugin com.diffplug.spotless to v6.20.0
- chore(deps): update plugin org.sonarqube to v4.3.0.3225
- fix(deps): update dependency org.junit.jupiter:junit-jupiter-api to v5.10.0
- chore(deps): update plugin org.owasp.dependencycheck to v8.4.0
- chore(deps): update plugin org.sonarqube to v4.3.1.3277
- chore(deps): update plugin com.diffplug.spotless to v6.21.0
- fix(deps): update dependency org.projectlombok:lombok to v1.18.30
- chore(deps): update plugin com.diffplug.spotless to v6.22.0
- chore(deps): update dependency gradle to v7.6.3
- chore(deps): update plugin org.owasp.dependencycheck to v8.4.2
- chore(deps): update plugin org.sonarqube to v4.4.1.3373
- fix(deps): update dependency org.junit.jupiter:junit-jupiter-api to v5.10.1
- chore(deps): update plugin org.owasp.dependencycheck to v8.4.3
- chore(deps): update plugin org.owasp.dependencycheck to v9
- chore(deps): update plugin org.owasp.dependencycheck to v9.0.2
- chore(deps): update plugin com.diffplug.spotless to v6.23.2

## [0.3.0] - 2022-05-06
### Added
- Dependency check plugin

### Changed
- Publication of artifacts to Sonatype Nexus due to bintray sunset
- Upgraded dependency versions
- Used `publish.gradle` and `checks.gradle` from `gradle-includes`
- Refactored CI pipeline with proper gradle caching

## [0.2.0] - 2020-11-04
### Added
- [Issue 13](https://github.com/devatherock/simple-yaml/issues/13): Static method to generate yaml
- Usage instructions to readme

### Changed
- [Issue 8](https://github.com/devatherock/simple-yaml/issues/8): Rewrote in Java
- [Issue 15](https://github.com/devatherock/simple-yaml/issues/15): Made the fields final

## [0.1.0] - 2020-10-11
### Added
- [Issue 7](https://github.com/devatherock/simple-yaml/issues/7): Unit tests
- [Issue 10](https://github.com/devatherock/simple-yaml/issues/10): Code coverage badge

### Changed
- Used groovy version `2.5.7` to work with `scriptjar`
- Included groovy as a `compileOnly` dependency to prevent runtime version conflicts
- Corrected applying quotes to numeric fields

## [0.0.1] - 2020-04-12
### Added
- Initial version written in groovy. Converts a `java.util.Map` into YAML