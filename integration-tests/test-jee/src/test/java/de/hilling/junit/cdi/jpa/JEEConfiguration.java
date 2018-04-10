package de.hilling.junit.cdi.jpa;

import de.hilling.junit.cdi.jee.JEETestConfiguration;
import de.hilling.junit.cdi.scope.TestSuiteScoped;

/**
 * Configuration provider for jee integration.
 */
@TestSuiteScoped
public class JEEConfiguration implements JEETestConfiguration {

    @Override
    public String getTestPersistenceUnitName() {
        return "cdi-test-unit";
    }
}
