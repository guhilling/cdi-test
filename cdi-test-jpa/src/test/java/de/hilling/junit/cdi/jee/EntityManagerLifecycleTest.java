package de.hilling.junit.cdi.jee;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.jboss.weld.transaction.spi.TransactionServices;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
@DisplayName("EntityManager Lifecycle")
@Transactional(Transactional.TxType.REQUIRES_NEW)
class EntityManagerLifecycleTest {

    @Inject
    private TransactionServices transactionServices;

    @PersistenceContext(unitName = "cdi-test")
    private EntityManager entityManagerJta;

    @PersistenceUnit(unitName = "cdi-test")
    private EntityManagerFactory entityManagerFactoryJta;

    @PersistenceContext(unitName = "cdi-test-local")
    private EntityManager entityManagerLocal;

    @PersistenceUnit(unitName = "cdi-test-local")
    private EntityManagerFactory entityManagerFactoryLocal;

    @Test
    void jtaInjected() {
        assertNotNull(entityManagerJta);
    }
    @Test
    void localInjected() {
        assertNotNull(entityManagerLocal);
    }

    @Test
    void localFactoryInjected() {
        assertNotNull(entityManagerFactoryLocal);
    }
    @Test
    void jtaFactoryInjected() {
        assertNotNull(entityManagerFactoryJta);
    }

    @Test
    void localTransactionNotActive() {
        assertFalse(entityManagerLocal.getTransaction().isActive());
    }

    @Test
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    void globalTransactionActive() {
        assertFalse(transactionServices.isTransactionActive());
        entityManagerJta.persist(new UserEntity());
        assertFalse(transactionServices.isTransactionActive());
    }

}
