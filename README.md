# FF4J Feature Flags Extension
[Precondition](https://docs.liquibase.com/concepts/changelogs/preconditions.html) to control the execution of a changelog or changeset based on the state of feature flags from FF4J.

## Supported Editions
[![Community](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/mcred/liquibase-header-footer/feature/badges/badges/community.json)](https://liquibase.org/)
[![Pro](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/mcred/liquibase-header-footer/feature/badges/badges/pro.json)](https://www.liquibase.com/pricing/pro)

## Installation
The easiest way to install this extension is with `lpm` [liquibase package manager](https://github.com/liquibase/liquibase-package-manager).
```shell
lpm update
lpm add ff4j
```

## Setup
The `ff4j_url` is required for the extension to know where the FF4J server is located. 
```
--ff4j-url=PARAM
     URL for FF4J Server
     (liquibase.ff4j.url)
     (LIQUIBASE_FF4J_URL)
     [deprecated: --ff4jUrl]
```

## Usage
To use this extension, add the `ff4jFeatureFlag` precondition to your Changelog or Changeset with an `enabledFlags` attribute. The value for `enabledFlags` is either a string with one feature flag key or a comma separated string with multiple feature flag keys. All feature flags must be enabled for the precondition to pass.

## Example
```yaml
databaseChangeLog:
  -  preConditions:
     -  ff4jFeatureFlag:
          enabledFlags: changelog-testing
```
```xml
<changeSet id="1" author="example">
    <preConditions>
        <ext:ff4jFeatureFlag enabledFlags="changelog-testing"/>
    </preConditions>
    ...
</changeSet>
```

## Feedback and Issues
Please submit all feedback and issues to [this idea board](https://ideas.liquibase.com/c/63-launchdarkly-feature-flags-pro-extension).