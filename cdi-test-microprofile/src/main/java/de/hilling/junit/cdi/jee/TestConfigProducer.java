package de.hilling.junit.cdi.jee;

import de.hilling.junit.cdi.scope.TestScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@TestScoped
public class TestConfigProducer {

    @Inject
    private TestConfig config;

    @Produces
    @Any
    @ConfigProperty
    public String getConfigurationString() {
        return null;
    }

    @Produces
    @Any
    @ConfigProperty
    public Integer getConfigurationInteger() {
        return null;
    }

    @Produces
    @Any
    @ConfigProperty
    public Boolean getConfigurationBoolean() {
        return null;
    }
}
