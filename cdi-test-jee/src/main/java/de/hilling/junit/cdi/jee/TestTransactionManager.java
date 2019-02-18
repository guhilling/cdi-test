package de.hilling.junit.cdi.jee;

import de.hilling.junit.cdi.CdiTestException;
import de.hilling.junit.cdi.jee.jpa.ConnectionWrapper;
import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.EventType;
import de.hilling.junit.cdi.scope.TestSuiteScoped;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.SQLException;

@TestSuiteScoped
public class TestTransactionManager {

    @Inject
    private Instance<ConnectionWrapper> connectionWrappers;
    @Inject
    private EntityManager               entityManager;
    private EntityTransaction           transaction;

    protected void beginTransaction(@Observes @TestEvent(EventType.STARTED) ExtensionContext description) {
        cleanDatabase();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    private void cleanDatabase() {
        try {
            for (ConnectionWrapper wrapper : connectionWrappers) {
                if(wrapper.callDatabaseCleaner()) {
                    break;
                }
            }
        } catch (SQLException e) {
            throw new CdiTestException("error cleaning db", e);
        }
    }

    protected void finishTransaction(@Observes @TestEvent(EventType.FINISHING) ExtensionContext description) {
        if (transaction.isActive()) {
            if (transaction.getRollbackOnly()) {
                transaction.rollback();
            } else {
                transaction.commit();
            }
        }
    }

}
