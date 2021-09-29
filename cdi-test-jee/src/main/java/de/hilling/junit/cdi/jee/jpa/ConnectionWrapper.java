package de.hilling.junit.cdi.jee.jpa;

import javax.persistence.EntityManager;

/**
 * Interface for classes providing wrapped connections.
 */
public interface ConnectionWrapper {

    /**
     * Execute database cleaner.
     *
     * @param entityManager Connection to use.
     */
    void callDatabaseCleaner(EntityManager entityManager);
}
