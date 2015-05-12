package de.hilling.junit.cdi.jee;

import de.hilling.junit.cdi.jee.jpa.DatabaseCleaner;
import de.hilling.junit.cdi.jee.jpa.hibernate.HibernateConnectionWrapper;
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

    public static final String HIBERNATE_INTERNAL_SESSION_IMPL = "org.hibernate.internal.SessionImpl";
    @Inject
    private Instance<EntityManager> entityManagerReference;
    @Inject
    private Instance<HibernateConnectionWrapper> hibernateConnectionResolver;
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
        Object delegate = entityManager.getDelegate();
        String delegateClassName = delegate.getClass().getCanonicalName();
        switch (delegateClassName) {
            case HIBERNATE_INTERNAL_SESSION_IMPL:
                hibernateConnectionResolver.get().runWithConnection(databaseCleaner);
                break;
            default:
                throw new RuntimeException("no wrapper for delegate " + delegateClassName);
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
