package de.hilling.junit.cdi.jee;

import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.EventType;
import de.hilling.junit.cdi.scope.TestSuiteScoped;
import org.junit.runner.Description;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@TestSuiteScoped
public class TestTransactionManager {

    @Inject
    private Instance<EntityManager> entityManager;
    private EntityTransaction transaction;

    protected void beginTransaction(@Observes @TestEvent(EventType.STARTING) Description description) {
        checkEntityManager();
        transaction = entityManager.get().getTransaction();
        transaction.begin();
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
        if (entityManager.isUnsatisfied()) {
            throw new RuntimeException("entity manager not active");
        }
    }
}
