package de.hilling.junit.cdi.jee;

import de.hilling.junit.cdi.annotations.TestImplementation;
import de.hilling.junit.cdi.scope.TestSuiteScoped;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Producer for EntityManagers used in cdi-test unit tests.
 */
@TestSuiteScoped
public class EntityManagerTestProducer {

    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    protected void createEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("cdi-test-unit");
    }

    @Produces
    @TestImplementation
    @RequestScoped
    protected EntityManagerFactory provideTestEntityManagerFactory() {
        return entityManagerFactory;
    }

    @Produces
    @TestImplementation
    @RequestScoped
    protected EntityManager provideTestEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
