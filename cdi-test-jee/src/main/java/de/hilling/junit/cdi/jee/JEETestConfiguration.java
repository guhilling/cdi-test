package de.hilling.junit.cdi.jee;

/**
 * Configuration for JEE test module.
 */
public interface JEETestConfiguration {

    /**
     * Provide test persistence unit name.
     *
     * @return name of the test persistence unit.
     */
     String getTestPersistenceUnitName();
}
