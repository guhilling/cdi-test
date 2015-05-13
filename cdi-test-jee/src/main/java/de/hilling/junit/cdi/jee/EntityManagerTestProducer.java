package de.hilling.junit.cdi.jee;

import de.hilling.junit.cdi.annotations.TestImplementation;
import de.hilling.junit.cdi.scope.TestSuiteScoped;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Producer for EntityManagers used in cdi-test unit tests.
 */
@TestSuiteScoped
public class EntityManagerTestProducer {
    public static final String PERSISTENCE_UNIT_PROPERTY="persistence-unit";

    private EntityManagerFactory entityManagerFactory;

    @Inject
    @Named(PERSISTENCE_UNIT_PROPERTY)
    private String persistenceUnit;

    @PostConstruct
    protected void createEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
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
