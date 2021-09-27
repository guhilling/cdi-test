package de.hilling.junit.cdi.jee;

import de.hilling.junit.cdi.annotations.GlobalTestImplementation;
import de.hilling.junit.cdi.scope.TestSuiteScoped;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * Producer for EntityManagers used in cdi-test unit tests.
 */
@TestSuiteScoped
public class EntityManagerTestProducer {
    @Inject
    private TestEntityManagerFactory entityManagerFactory;

    @Produces
    @GlobalTestImplementation
    @RequestScoped
    protected EntityManagerFactory provideTestEntityManagerFactory() {
        return entityManagerFactory.resolveEntityManagerFactory("cdi-test-unit");
    }

    @Produces
    @GlobalTestImplementation
    @RequestScoped
    protected EntityManager provideTestEntityManager() {
        return entityManagerFactory.resolveEntityManager("cdi-test-unit");
    }

    @Produces
    @GlobalTestImplementation
    @RequestScoped
    @SecondEntityManager
    protected EntityManager provideTestEntityManagerB() {
        return entityManagerFactory.resolveEntityManager("cdi-test-unit-b");
    }
}

