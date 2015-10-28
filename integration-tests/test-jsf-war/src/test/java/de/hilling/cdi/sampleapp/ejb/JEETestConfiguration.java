package de.hilling.cdi.sampleapp.ejb;

import de.hilling.junit.cdi.annotations.GlobalTestImplementation;
import de.hilling.junit.cdi.scope.TestSuiteScoped;

@GlobalTestImplementation
@TestSuiteScoped
public class JEETestConfiguration implements de.hilling.junit.cdi.jee.JEETestConfiguration {
    @Override
    public String getTestPersistenceUnitName() {
        return "cdi-test-unit-eclipselink";
    }
}
