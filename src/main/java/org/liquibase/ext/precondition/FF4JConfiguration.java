package org.liquibase.ext.precondition;

import liquibase.configuration.AutoloadedConfigurations;
import liquibase.configuration.ConfigurationDefinition;

public class FF4JConfiguration implements AutoloadedConfigurations {

    public static ConfigurationDefinition<String> FF4J_URL;

    static {
        ConfigurationDefinition.Builder builder = new ConfigurationDefinition.Builder("liquibase.ff4j");
        FF4J_URL = builder.define("url", String.class)
                .addAliasKey("ff4j.url")
                .setDescription("URL for FF4J Server")
                .build();
    }
}
