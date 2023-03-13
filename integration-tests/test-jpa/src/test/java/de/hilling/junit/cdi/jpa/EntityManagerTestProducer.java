package de.hilling.junit.cdi.jpa;

import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

import de.hilling.junit.cdi.scope.TestSuiteScoped;

/**
 * Producer for EntityManagers used in jpa unit tests.
 */
@TestSuiteScoped
public class EntityManagerTestProducer {

    @PersistenceUnit(unitName = "cdi-test-unit")
    private EntityManagerFactory entityManagerFactory;

    @Produces
    public EntityManager createEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

}

