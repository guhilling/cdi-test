package de.hilling.junit.cdi.jee.jpa;

import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.Status;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transactional;
import jakarta.transaction.UserTransaction;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.jupiter.api.extension.ExtensionContext;

import de.hilling.junit.cdi.CdiTestException;
import de.hilling.junit.cdi.ContextControlWrapper;
import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.TestScoped;
import de.hilling.junit.cdi.scope.TestState;

/**
 * Track created {@link EntityManager} and {@link EntityTransaction} and dispose of them after Tests.
 */
@TestScoped
public class TestEntityResources {
    private static final Logger LOG = Logger.getLogger(TestEntityResources.class.getCanonicalName());

    private final Map<String, EntityManager>        entityManagers         = new HashMap<>();
    private final Map<String, EntityManagerFactory> entityManagerFactories = new HashMap<>();

    @Inject
    private Instance<ConnectionWrapper> connectionWrappers;

    @Inject
    private UserTransaction userTransaction;

    /**
     * The {@link EntityManager} for given persistence unit.
     *
     * @param name persistence unit name.
     * @return {@link EntityManager} for given persistence unit.
     */
    public synchronized EntityManager resolveEntityManager(String name) {
        return entityManagers.computeIfAbsent(name, this::createEntityManager);
    }

    private EntityManager createEntityManager(String name) {
        EntityManagerFactory emf = resolveEntityManagerFactory(name);
        EntityManager entityManager = emf.createEntityManager();
        connectionWrappers.stream().forEach(cw -> cw.callDatabaseCleaner(entityManager));
        return entityManager;
    }

    public EntityManagerFactory resolveEntityManagerFactory(String name) {
        return entityManagerFactories.computeIfAbsent(name, this::createEntityManagerFactory);
    }

    private EntityManagerFactory createEntityManagerFactory(String persistenceUnit) {
        BeanManager beanManager = ContextControlWrapper.getInstance().getContextualReference(BeanManager.class);
        Map<String, Object> props = new HashMap<>();
        props.put("jakarta.persistence.bean.manager", beanManager);
        props.put("javax.persistence.bean.manager", beanManager);
        return Persistence.createEntityManagerFactory(persistenceUnit, props);
    }

    /**
     * Cleanup when test finished.
     *
     * @param description only used for triggering observer.
     */
    protected void startTransactions(@Observes @TestEvent(TestState.STARTED) ExtensionContext description) {
        if(getTxBehaviour(description) == Transactional.TxType.REQUIRES_NEW) {
            try {
                userTransaction.begin();
            } catch (NotSupportedException | SystemException e) {
                throw new CdiTestException("Starting user transaction failed", e);
            }
        }
    }

    protected void finishResources(@Observes @TestEvent(TestState.FINISHING) ExtensionContext description) {
        if(getTxBehaviour(description) == Transactional.TxType.REQUIRES_NEW) {
            try {
                final int status = userTransaction.getStatus();
                if(status == Status.STATUS_ACTIVE) {
                    LOG.fine("Committing TX");
                    userTransaction.commit();
                } else if (status == Status.STATUS_MARKED_ROLLBACK) {
                    LOG.fine("Rolling back TX marked as rollback");
                    userTransaction.rollback();
                } else if (status == Status.STATUS_NO_TRANSACTION) {
                    LOG.warning("No TX active though REQUIRES_NEW was set");
                }
            } catch (SystemException | RollbackException | HeuristicMixedException |
                     HeuristicRollbackException e) {
                throw new CdiTestException("Committing user transaction failed", e);
            }
        } else {
            try {
                if (userTransaction.getStatus() != Status.STATUS_NO_TRANSACTION) {
                    LOG.warning("Closing open user transaction after test: " + userTransaction);
                    userTransaction.rollback();
                }
            } catch (SystemException e) {
                throw new CdiTestException("Closing user transaction failed", e);
            }
        }
        entityManagers.values().forEach(EntityManager::close);
        entityManagers.clear();
        entityManagerFactories.clear();
    }

    private Transactional.TxType getTxBehaviour(ExtensionContext description) {
        Transactional classAnnotation = description.getRequiredTestMethod().getAnnotation(Transactional.class);
        Transactional methodAnnotation = description.getRequiredTestClass().getAnnotation(Transactional.class);
        if(methodAnnotation != null) {
            return methodAnnotation.value();
        } else {
            if(classAnnotation == null) {
                return Transactional.TxType.REQUIRES_NEW;
            } else {
                return classAnnotation.value();
            }
        }
    }
}
