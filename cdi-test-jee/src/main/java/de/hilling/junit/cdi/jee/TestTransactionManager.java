package de.hilling.junit.cdi.jee;

import de.hilling.junit.cdi.CdiTestException;
import de.hilling.junit.cdi.jee.jpa.ConnectionWrapper;
import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.TestState;
import de.hilling.junit.cdi.scope.TestSuiteScoped;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@TestSuiteScoped
public class TestTransactionManager {

    @Inject
    private Instance<ConnectionWrapper>    connectionWrappers;
    private Map<String, EntityTransaction> transactions = new HashMap<>();
    @Inject
    private TestEntityManagerFactory       testEntityManagerFactory;

    protected void beginTransaction(@Observes @TestEvent(TestState.STARTED) ExtensionContext description) {
        cleanDatabase();
        testEntityManagerFactory.getEntityManagers().entrySet().forEach(this::startTransaction);
    }

    private void startTransaction(Map.Entry<String, EntityManager> stringEntityManagerEntry) {
        String name = stringEntityManagerEntry.getKey();
        EntityManager entityManager = stringEntityManagerEntry.getValue();
        EntityTransaction transaction = entityManager.getTransaction();
        transactions.put(name, transaction);
        transaction.begin();
    }

    private void cleanDatabase() {
        try {
            for (ConnectionWrapper wrapper : connectionWrappers) {
                wrapper.callDatabaseCleaner();
            }
        } catch (SQLException e) {
            throw new CdiTestException("error cleaning db", e);
        }
    }

    protected void finishTransaction(@Observes @TestEvent(TestState.FINISHING) ExtensionContext description) {
        transactions.values().forEach(this::finishTransaction);
        transactions.clear();
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

}
