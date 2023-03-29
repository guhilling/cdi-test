package de.hilling.junit.cdi.jee.jpa;

import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;

import java.util.HashMap;
import java.util.Map;

import org.jboss.weld.transaction.spi.TransactionServices;
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

    private final Map<String, EntityManager>        entityManagers         = new HashMap<>();
    private final Map<String, EntityManagerFactory> entityManagerFactories = new HashMap<>();

    @Inject
    private Instance<ConnectionWrapper> connectionWrappers;

    @Inject
    private UserTransaction userTransaction;

    @Inject
    private TransactionServices transactionServices;

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

    protected void finishResources(@Observes @TestEvent(TestState.FINISHING) ExtensionContext description) {
        if(transactionServices.isTransactionActive()) {
            try {
                userTransaction.rollback();
            } catch (SystemException e) {
                throw new CdiTestException("cannot rollback", e);
            }
        }
        entityManagers.values().forEach(EntityManager::close);
        entityManagers.clear();
        entityManagerFactories.clear();
    }
}
