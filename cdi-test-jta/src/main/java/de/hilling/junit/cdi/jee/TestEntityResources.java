package de.hilling.junit.cdi.jee;

import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.extension.ExtensionContext;

import de.hilling.junit.cdi.jee.jpa.ConnectionWrapper;
import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.TestScoped;
import de.hilling.junit.cdi.scope.TestState;

/**
 * Track created {@link EntityManager} and {@link EntityTransaction} and dispose of them after Tests.
 */
@TestScoped
public class TestEntityResources {

    private final Map<String, EntityManager> entityManagers = new HashMap<>();

    @Inject
    private Instance<ConnectionWrapper> connectionWrappers;

    /**
     * State of the {@link EntityManager} for given persistence unit.
     *
     * @param name persistence unit name.
     * @return true if {@link EntityManager} for given persistence unit is already available.
     */
    public boolean hasEntityManager(String name) {
        return entityManagers.containsKey(name);
    }

    /**
     * The {@link EntityManager} for given persistence unit.
     *
     * @param name persistence unit name.
     * @return {@link EntityManager} for given persistence unit.
     */
    public EntityManager getEntityManager(String name) {
        return entityManagers.get(name);
    }

    /**
     * Cleanup when test finished.
     *
     * @param description only used for triggering observer.
     */
    protected void finishResources(@Observes @TestEvent(TestState.FINISHING) ExtensionContext description) {
        entityManagers.values().forEach(EntityManager::close);
        entityManagers.clear();
    }

    /**
     * Add new {@link EntityManager} for given unit name.
     *
     * @param name          unit name.
     * @param entityManager manager.
     */
    public void putEntityManager(String name, EntityManager entityManager) {
        entityManagers.put(name, entityManager);
        connectionWrappers.stream().forEach(cw -> cw.callDatabaseCleaner(entityManager));
    }
}
