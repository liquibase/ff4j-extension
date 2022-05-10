package org.liquibase.ext.precondition;

import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;

import java.io.IOException;

public class FF4JFeatureFlagPrecondition extends AbstractFeatureFlagPrecondition {

    public static FF4J ff4j;

    static {
        ff4j = new FF4J(
                FF4JConfiguration.FF4J_URL.getCurrentValue()
        );
    }

    @Override
    public String getName() {
        return "ff4jFeatureFlag";
    }

    @Override
    public void check(Database database, DatabaseChangeLog databaseChangeLog, ChangeSet changeSet, ChangeExecListener changeExecListener) throws PreconditionFailedException, PreconditionErrorException {
        try {
            for (String flag: this.getEnabledFlags()) {
                if (!ff4j.Enabled(flag.replaceAll("\\s", ""))) {
                    throw new PreconditionFailedException(this.getName() + "->" + flag + " is not enabled.", databaseChangeLog, this);
                }
            }
        } catch (IOException e) {
            throw new PreconditionErrorException(e, databaseChangeLog, this);
        }
    }
}
