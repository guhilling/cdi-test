package de.hilling.junit.cdi.jee;

import de.hilling.junit.cdi.jee.jpa.ConnectionWrapper;
import de.hilling.junit.cdi.jee.jpa.DatabaseCleaner;
import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.EventType;
import de.hilling.junit.cdi.scope.TestSuiteScoped;
import org.junit.runner.Description;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.SQLException;

@TestSuiteScoped
public class TestTransactionManager {

    public static final String HIBERNATE_DELEGATE = "org.hibernate.internal.SessionImpl";
    public static final String ECLIPSELINK_DELEGATE = "org.eclipse.persistence.jpa.JpaEntityManager";

    @Inject
    private Instance<EntityManager> entityManagerReference;
    @Inject
    private Instance<ConnectionWrapper> connectionWrappers;
    @Inject
    private DatabaseCleaner databaseCleaner;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    protected void beginTransaction(@Observes @TestEvent(EventType.STARTING) Description description) {
        checkEntityManager();
        cleanDatabase();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    private void cleanDatabase() {
        try {
            for (ConnectionWrapper wrapper : connectionWrappers) {
                wrapper.runWithConnection(databaseCleaner);
            }
        } catch (SQLException e) {
            throw new RuntimeException("error cleaning db", e);
        }
    }

    protected void finishTransaction(@Observes @TestEvent(EventType.FINISHING) Description description) {
        checkEntityManager();
        if (transaction.getRollbackOnly()) {
            transaction.rollback();
        } else {
            transaction.commit();
        }
    }

    private void checkEntityManager() {
        if (entityManagerReference.isUnsatisfied()) {
            throw new RuntimeException("entity manager not active");
        } else if (entityManagerReference.isAmbiguous()) {
            throw new RuntimeException("more than one entity manager found");
        } else {
            entityManager = entityManagerReference.get();
        }
    }
}
