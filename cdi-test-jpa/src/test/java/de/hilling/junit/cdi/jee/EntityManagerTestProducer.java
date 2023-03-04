package de.hilling.junit.cdi.jee;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import de.hilling.junit.cdi.annotations.GlobalTestImplementation;
import de.hilling.junit.cdi.scope.TestSuiteScoped;

/**
 * Producer for EntityManagers used in cdi-test unit tests.
 */

@TestSuiteScoped
public class EntityManagerTestProducer {
    @Inject
    private EntityManagerResourcesProvider entityManagerFactory;

    @Produces
    @GlobalTestImplementation
    @RequestScoped
    protected EntityManagerFactory provideJtaTestEntityManagerFactory() {
        return entityManagerFactory.resolveEntityManagerFactory("cdi-test-unit-jta");
    }

    @Produces
    @GlobalTestImplementation
    @RequestScoped
    protected EntityManager provideJtaTestEntityManager() {
        return entityManagerFactory.resolveEntityManager("cdi-test-unit-jta");
    }

}

