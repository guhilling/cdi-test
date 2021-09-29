package de.hilling.junit.cdi.jee;

import de.hilling.junit.cdi.scope.TestSuiteScoped;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

/**
 * Producer for EntityManagers used in cdi-test unit tests.
 */
@TestSuiteScoped
public class TestEntityManagerFactory {
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
    public EntityManagerFactory resolveEntityManagerFactory(String persistenceUnitName) {
        synchronized (this) {
            if (!factories.containsKey(persistenceUnitName)) {
                Map<String, Object> props = new HashMap<>();
                props.put("javax.persistence.bean.manager", beanManager);
                factories.put(persistenceUnitName, Persistence.createEntityManagerFactory(persistenceUnitName, props));
            }
            return factories.get(persistenceUnitName);
        }
    }

    /**
     * Provide an {@link EntityManager } for the persistence unit with the given name. The scope is per Request, i.e. the {@link EntityManager} will
     * be closed after the request, typically after the test.
     *
     * @param persistenceUnitName name of persistence unit to resolve.
     * @return EntityManager for current request.
     */
    public EntityManager resolveEntityManager(String persistenceUnitName) {
        synchronized (this) {
            if (!testEntityResources.getEntityManagers().containsKey(persistenceUnitName)) {
                testEntityResources.putEntityManager(persistenceUnitName, resolveEntityManagerFactory(persistenceUnitName).createEntityManager());
            }
            return testEntityResources.getEntityManagers().get(persistenceUnitName);
        }
    }

}
