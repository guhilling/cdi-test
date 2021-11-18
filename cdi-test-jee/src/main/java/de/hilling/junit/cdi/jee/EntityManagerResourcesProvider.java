package de.hilling.junit.cdi.jee;

import de.hilling.junit.cdi.scope.TestSuiteScoped;

import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

/**
 * Provider/Factory for {@link EntityManager}s used in cdi-test unit tests.
 * <br>
 * Lookup you resources ({@link EntityManager} and {@link EntityManagerFactory} using this class. Transactions and cleanup will be handled
 * automatically when unit tests are started and finished.
 * <br>
 * See examples in the integration-tests/test-jee package and in the unit tests for this module.
 */
@TestSuiteScoped
public class EntityManagerResourcesProvider {
    private Map<String, EntityManagerFactory> factories = new HashMap<>();

    @Inject
    private TestEntityResources testEntityResources;

    @Inject
    private BeanManager beanManager;

    /**
     * Provide an {@link EntityManagerFactory} for the persistence unit with the given name. The scope is global, i.e. static.
     *
     * @param persistenceUnitName name of persistence unit to resolve.
     * @return EntityManagerFactory for current test run.
     */
    public synchronized EntityManagerFactory resolveEntityManagerFactory(String persistenceUnitName) {
        return factories.computeIfAbsent(persistenceUnitName, this::createEntityManagerFactory);
    }

    private EntityManagerFactory createEntityManagerFactory(String persistenceUnit) {
        Map<String, Object> props = new HashMap<>();
        props.put("javax.persistence.bean.manager", beanManager);
        return Persistence.createEntityManagerFactory(persistenceUnit, props);
    }

    /**
     * Provide an {@link EntityManager } for the persistence unit with the given name. The scope is per Request, i.e. the {@link EntityManager} will
     * be closed after the request, typically after the test.
     *
     * @param persistenceUnitName name of persistence unit to resolve.
     * @return EntityManager for current request.
     */
    public synchronized EntityManager resolveEntityManager(String persistenceUnitName) {
        if (!testEntityResources.hasEntityManager(persistenceUnitName)) {
            testEntityResources.putEntityManager(persistenceUnitName, resolveEntityManagerFactory(persistenceUnitName).createEntityManager());
        }
        return testEntityResources.getEntityManager(persistenceUnitName);
    }

}
