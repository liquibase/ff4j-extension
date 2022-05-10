package org.liquibase.ext.precondition

import liquibase.changelog.ChangeSet
import liquibase.exception.PreconditionErrorException
import liquibase.exception.PreconditionFailedException
import liquibase.parser.ChangeLogParserFactory

class FF4JFeatureFlagPreconditionTest extends AbstractFeatureFlagPreconditionTest {

    def dbChangeLog = ChangeLogParserFactory.getInstance().getParser("xml", clra)
            .parse("changelogs/ff4j.xml", null, clra)
    def changeSet = new ChangeSet(dbChangeLog)
    def ff4jFeatureFlagPrecondition = dbChangeLog.getChangeSets()[0].getPreconditions().getNestedPreconditions()[0]

    def "GetName"() {
        expect:
        ff4jFeatureFlagPrecondition.getName() == "ff4jFeatureFlag"
    }

    def "FF4J feature flag is not available"() {
        when:
        ff4jFeatureFlagPrecondition.ff4j = Mock(FF4J)
        ff4jFeatureFlagPrecondition.ff4j.Enabled(*_) >> { throw new IOException("Unable to connect to FF4J API.") }
        ff4jFeatureFlagPrecondition.check(database, dbChangeLog, changeSet, changeExecListener)

        then:
        thrown(PreconditionErrorException)
    }

    def "FF4J feature flag is not found"() {
        when:
        ff4jFeatureFlagPrecondition.ff4j = Mock(FF4J)
        ff4jFeatureFlagPrecondition.ff4j.Enabled(*_) >> { false }
        ff4jFeatureFlagPrecondition.check(database, dbChangeLog, changeSet, changeExecListener)

        then:
        thrown(PreconditionFailedException)
    }

    def "FF4J feature flag is not enabled"() {
        when:
        ff4jFeatureFlagPrecondition.ff4j = Mock(FF4J)
        ff4jFeatureFlagPrecondition.ff4j.Enabled(*_) >> { false }
        ff4jFeatureFlagPrecondition.check(database, dbChangeLog, changeSet, changeExecListener)

        then:
        thrown(PreconditionFailedException)
    }

    def "FF4J feature flag is enabled"() {
        given:
        ff4jFeatureFlagPrecondition.ff4j = Mock(FF4J)
        ff4jFeatureFlagPrecondition.ff4j.Enabled(*_) >> { true }

        expect:
        ff4jFeatureFlagPrecondition.check(database, dbChangeLog, changeSet, changeExecListener)
    }
}
