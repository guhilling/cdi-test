package de.hilling.junit.cdi.jee;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transactional;
import jakarta.transaction.UserTransaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.jboss.weld.transaction.spi.TransactionServices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
@Transactional(Transactional.TxType.NEVER)
class TransactionalScopesTest {
    @Inject
    private ExecutionService executionService;

    @Inject
    private TransactionServices transactionServices;

    @PersistenceContext
    private EntityManager entityManager;

    @PersistenceContext(unitName = "cdi-test-localcopy")
    private EntityManager entityManagerLocal;

    @Inject
    private UserTransaction userTransaction;

    @Test
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    void userTransactionIdentical() {
        String userTransactionDescription = userTransaction.toString();
        String transactionServicesTransactionDescription = transactionServices.getUserTransaction().toString();
        assertEquals(transactionServicesTransactionDescription, userTransactionDescription);
    }

    @Test
    void finishTransactional() {
        long userId = executionService.executeRequired(this::createUser);
        assertUserStored(userId);
    }

    @Test
    void finishTransactionalLocalRollback() throws SystemException, NotSupportedException {
        userTransaction.begin();
        executionService.executeRequired(this::createUser);
        userTransaction.rollback();
        assertNoUsers();
    }

    @Test
    void finishTransactionalNewLocalRollback() throws SystemException, NotSupportedException {
        userTransaction.begin();
        long userId = executionService.executeRequiresNew(this::createUser);
        userTransaction.rollback();
        assertUserStored(userId);
    }

    @Test
    void rollbackTransactional() {
        Assertions.assertThrows(RuntimeException.class, () -> executionService.executeRequired(this::createUserThrow));
        assertNoUsers();
    }

    @Test
    void failTransactionActiveWhenTxNever() throws SystemException, NotSupportedException {
        userTransaction.begin();
        Assertions.assertThrows(RuntimeException.class, () -> executionService.executeTxNever(this::createUser));
    }

    @Test
    void finishLocalTransactionWhenTxNever() {
        long userId = executionService.executeTxNeverLocal(this::createUserLocal);
        assertUserStored(userId);
    }

    @Test
    void forbidLocalTransactionForJpa() {
        Assertions.assertThrows(IllegalStateException.class, () -> entityManager.getTransaction());
    }

    private void assertNoUsers() {
        List<UserEntity> users = entityManagerLocal.createNamedQuery("findAllUserEntity", UserEntity.class)
                                                   .getResultList();
        Assertions.assertEquals(0, users.size());
        users = entityManager.createNamedQuery("findAllUserEntity", UserEntity.class)
                                                   .getResultList();
        Assertions.assertEquals(0, users.size());
    }

    private void assertUserStored(long userId) {
        assertNotNull(entityManagerLocal.find(UserEntity.class, userId));
        assertNotNull(entityManager.find(UserEntity.class, userId));
    }

    private long createUser(EntityManager entityManager) {
        UserEntity user = new UserEntity();
        entityManager.persist(user);
        return user.getId();
    }

    private long createUserLocal(EntityManager entityManager) {
        UserEntity user = new UserEntity();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        return user.getId();
    }

    private long createUserThrow(EntityManager entityManager) {
        UserEntity user = new UserEntity();
        entityManager.persist(user);
        throw new RuntimeException("test exception");
    }
}
