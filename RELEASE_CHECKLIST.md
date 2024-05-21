# Release Checklist
This document outlines the steps to be taken to release a new version of the project.
As this project publishes the artifact to Maven Central, once a version is published, it cannot be overridden. 

## Pre-release:
### Create PR with following changes:
- [ ] Update the version in the [`gradle.properties`](./gradle.properties) file.
- [ ] Update the [CHANGELOG.md](./CHANGELOG.md) file. 

## Release:
- [ ] Run Release workflow manually in Github.