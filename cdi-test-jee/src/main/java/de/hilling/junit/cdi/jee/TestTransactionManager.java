package de.hilling.junit.cdi.jee;

import java.sql.SQLException;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.runner.Description;

import de.hilling.junit.cdi.jee.jpa.ConnectionWrapper;
import de.hilling.junit.cdi.jee.jpa.DatabaseCleaner;
import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.EventType;
import de.hilling.junit.cdi.scope.TestSuiteScoped;

@TestSuiteScoped
public class TestTransactionManager {

    public static final String HIBERNATE_DELEGATE = "org.hibernate.internal.SessionImpl";
    public static final String ECLIPSELINK_DELEGATE = "org.eclipse.persistence.jpa.JpaEntityManager";

    @Inject
    private Instance<ConnectionWrapper> connectionWrappers;
    @Inject
    private EntityManager               entityManager;
    private EntityTransaction           transaction;

    protected void beginTransaction(@Observes @TestEvent(EventType.STARTING) Description description) {
        cleanDatabase();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    private void cleanDatabase() {
        try {
            for (ConnectionWrapper wrapper : connectionWrappers) {
                if(wrapper.runWithConnection()) {
                    break;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("error cleaning db", e);
        }
    }

    protected void finishTransaction(@Observes @TestEvent(EventType.FINISHING) Description description) {
        if (transaction.isActive()) {
            if (transaction.getRollbackOnly()) {
                transaction.rollback();
            } else {
                transaction.commit();
            }
        }
    }

}
