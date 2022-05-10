package org.liquibase.ext.precondition;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.precondition.AbstractPrecondition;

public abstract class AbstractFeatureFlagPrecondition extends AbstractPrecondition {
    private String enabledFlags;

    public String[] getEnabledFlags() {
        return enabledFlags.split(",");
    }

    public void setEnabledFlags(String enabledFlags) {
        this.enabledFlags = enabledFlags;
    }

    @Override
    public Warnings warn(Database database) {
        return new Warnings();
    }

    @Override
    public ValidationErrors validate(Database database) {
        return new ValidationErrors()
                .checkRequiredField("enabledFlags", getEnabledFlags());
    }

    @Override
    public String getSerializedObjectNamespace() {
        return GENERIC_CHANGELOG_EXTENSION_NAMESPACE;
    }
}
