package de.hilling.junit.cdi.jee;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.Status;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
@Order(-1)
class EntityManagerLifecycleTest {

    @Inject
    private UserTransaction userTransaction;

    @PersistenceContext(unitName = "cdi-test")
    private EntityManager entityManagerJta;

    @PersistenceUnit(unitName = "cdi-test")
    private EntityManagerFactory entityManagerFactoryJta;

    @PersistenceContext(unitName = "cdi-test-local")
    private EntityManager entityManagerLocal;

    @PersistenceUnit(unitName = "cdi-test-local")
    private EntityManagerFactory entityManagerFactoryLocal;

    @Test
    void entityManagersInjected() {
        assertNotNull(entityManagerJta);
        assertNotNull(entityManagerLocal);
    }

    @Test
    void entityManagerFactoriesInjected() {
        assertNotNull(entityManagerFactoryJta);
        assertNotNull(entityManagerFactoryLocal);
    }

    @Test
    void localTransactionNotActive() {
        assertFalse(entityManagerLocal.getTransaction().isActive());
    }

    @Test
    void localTransactionAvailableEvenForJtaManager() {
        entityManagerJta.getTransaction().begin();
        // no assertion needed
    }

    @Test
    void globalTransactionActive() throws SystemException {
        assertEquals(Status.STATUS_ACTIVE, userTransaction.getStatus());
    }

}
