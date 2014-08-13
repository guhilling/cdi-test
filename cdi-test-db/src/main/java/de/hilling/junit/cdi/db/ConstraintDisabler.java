package de.hilling.junit.cdi.db;

/**
 * Disable and reenable constraints for certain db types.
 */
public interface ConstraintDisabler {
    /**
     * After calling all contraints on the connection will be disabled.
     */
    void disableConstraints();

    /**
     * After this call all constraints previously disable shall be
     */
    void enableConstraints();
}
