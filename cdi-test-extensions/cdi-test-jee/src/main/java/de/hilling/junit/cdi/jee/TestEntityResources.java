package de.hilling.junit.cdi.jee;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PreRemove;

import org.junit.jupiter.api.extension.ExtensionContext;

import de.hilling.junit.cdi.jee.jpa.ConnectionWrapper;
import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.TestScoped;
import de.hilling.junit.cdi.scope.TestState;

@TestScoped
public class TestEntityResources {

    private Map<String, EntityManager>     entityManagers = new HashMap<>();
    private Map<String, EntityTransaction> transactions   = new HashMap<>();

    @Inject
    private Instance<ConnectionWrapper> connectionWrappers;

    public Map<String, EntityManager> getEntityManagers() {
        return entityManagers;
    }

    public Map<String, EntityTransaction> getTransactions() {
        return transactions;
    }

    protected void finishResources(@Observes @TestEvent(TestState.FINISHING) ExtensionContext description) {
        transactions.values().forEach(this::finishTransaction);
        transactions.clear();
        entityManagers.values().forEach(EntityManager::close);
        entityManagers.clear();
    }

    private void finishTransaction(EntityTransaction transaction) {
        if (transaction.isActive()) {
            if (transaction.getRollbackOnly()) {
                transaction.rollback();
            } else {
                transaction.commit();
            }
        }
    }

    public void putEntityManager(String name, EntityManager entityManager) {
        entityManagers.put(name, entityManager);
        connectionWrappers.stream().forEach(cw -> cw.callDatabaseCleaner(entityManager));
        startTransaction(name, entityManager);
    }

    private void startTransaction(String name, EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();
        transactions.put(name, transaction);
        transaction.begin();
    }


}
