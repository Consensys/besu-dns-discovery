# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.0.0] - 2024-05-22

### Added

- Initial project forked from Tuweni [dns discovery](https://github.com/tmio/tuweni/tree/main/dns-discovery)

### Changed

- Move DNS error log to debug level as it is spammy[#2](https://github.com/Consensys/besu-dns-discovery/pull/2)
- Catch all exceptions to avoid any uncaught exceptions [#3](https://github.com/Consensys/besu-dns-discovery/pull/3)
- Don't wait 10 minutes (initial delay) but start DNS discovery immediately [#4](https://github.com/Consensys/besu-dns-discovery/pull/4)

## [0.0.1] - 2024-05-21

### Added

- Project and repository setup

[unreleased]: https://github.com/Consensys/besu-dns-discovery/compare/v1.0.0...HEAD
[1.0.0]: https://github.com/Consensys/besu-dns-discovery/compare/v0.0.1...v1.0.0
[0.0.1]: https://github.com/Consensys/besu-dns-discovery/releases/tag/v0.0.1
