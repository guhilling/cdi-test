package de.hilling.junit.cdi.jee;

import de.hilling.junit.cdi.scope.TestSuiteScoped;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PreRemove;

import java.util.HashMap;
import java.util.Map;

/**
 * Producer for EntityManagers used in cdi-test unit tests.
 */
@TestSuiteScoped
public class TestEntityManagerFactory {
    private static final Map<String, EntityManagerFactory> FACTORIES      = new HashMap<>();

    private Map<String, EntityManager> entityManagers = new HashMap<>();

    /**
     * Provide an {@link EntityManagerFactory} for the persistence unit with the given name.
     * The scope is global, i.e. static.
     *
     * @param persistenceUnitName name of persistence unit to resolve.
     * @return EntityManagerFactory for current test run.
     */
    public EntityManagerFactory resolveEntityManagerFactory(String persistenceUnitName) {
        synchronized (this) {
            if(!FACTORIES.containsKey(persistenceUnitName)) {
                FACTORIES.put(persistenceUnitName, Persistence.createEntityManagerFactory(persistenceUnitName));
            }
            return FACTORIES.get(persistenceUnitName);
        }
    }

    /**
     * Provide an {@link EntityManager } for the persistence unit with the given name.
     * The scope is per Request, i.e. the {@link EntityManager} will be closed after the request, typically after the test.
     *
     * @param persistenceUnitName name of persistence unit to resolve.
     * @return EntityManager for current request.
     */
    public EntityManager resolveEntityManager(String persistenceUnitName) {
        synchronized (this) {
            if(!entityManagers.containsKey(persistenceUnitName)) {
                entityManagers.put(persistenceUnitName, resolveEntityManagerFactory(persistenceUnitName).createEntityManager());
            }
            return entityManagers.get(persistenceUnitName);
        }
    }

    public Map<String, EntityManager> getEntityManagers() {
        return entityManagers;
    }

    @PreRemove
    public void close() {
        entityManagers.values().forEach(EntityManager::close);
    }
}
