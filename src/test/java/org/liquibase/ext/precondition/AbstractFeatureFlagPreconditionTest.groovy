package org.liquibase.ext.precondition

import liquibase.changelog.visitor.LoggingChangeExecListener
import liquibase.database.core.MockDatabase
import liquibase.Scope
import liquibase.license.LicenseService
import liquibase.license.LicenseServiceFactory
import liquibase.resource.ClassLoaderResourceAccessor
import spock.lang.Specification

class AbstractFeatureFlagPreconditionTest extends Specification {
    def database = new MockDatabase()
    def clra =  new ClassLoaderResourceAccessor()
    def changeExecListener = new LoggingChangeExecListener()
}
