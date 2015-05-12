package de.hilling.junit.cdi.jee.jpa;

/**
 * Interface for classes providing wrapped connections.
 */
public interface ConnectionWrapper {
    void runWithConnection(Work work);
}
