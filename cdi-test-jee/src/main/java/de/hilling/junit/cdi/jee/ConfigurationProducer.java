package de.hilling.junit.cdi.jee;

import de.hilling.junit.cdi.scope.TestSuiteScoped;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

/**
 * Configuration provider for jee integration.
 */
@TestSuiteScoped
public class ConfigurationProducer {

    @Produces
    @Named(EntityManagerTestProducer.PERSISTENCE_UNIT_PROPERTY)
    protected String getPersistenceUnit() {
        return "cdi-test-unit";
    }
}
