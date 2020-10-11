# Changelog

## [Unreleased]
### Added
- [Issue 13](https://github.com/devatherock/simple-yaml/issues/13): Static method to generate yaml

### Changed
- [Issue 8](https://github.com/devatherock/simple-yaml/issues/8): Rewrote in Java

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