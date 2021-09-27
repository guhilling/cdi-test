package de.hilling.junit.cdi.jee;

/**
 * Configuration for JEE test module.
 */
public interface JEETestConfiguration {

    /**
     * Provide test persistence unit name.
     *
     * @deprecated use naming convention instead.
     *
     * @return name of the test persistence unit.
     */
    @Deprecated
    String getTestPersistenceUnitName();
}
