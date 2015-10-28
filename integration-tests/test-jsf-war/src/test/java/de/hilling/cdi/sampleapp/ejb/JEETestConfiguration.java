package de.hilling.cdi.sampleapp.ejb;

import de.hilling.junit.cdi.annotations.GlobalTestImplementation;

@GlobalTestImplementation
public class JEETestConfiguration implements de.hilling.junit.cdi.jee.JEETestConfiguration {
    @Override
    public String getTestPersistenceUnitName() {
        return "cdi-test-unit-eclipselink";
    }
}
