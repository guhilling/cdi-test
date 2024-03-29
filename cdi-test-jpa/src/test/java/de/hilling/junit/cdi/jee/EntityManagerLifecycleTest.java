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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
@DisplayName("EntityManager Lifecycle")
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
    void globalTransactionActive() throws SystemException {
        assertEquals(Status.STATUS_ACTIVE, userTransaction.getStatus());
    }

}
