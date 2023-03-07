package de.hilling.junit.cdi.jee;

import de.hilling.junit.cdi.CdiTestJunitExtension;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(CdiTestJunitExtension.class)
class UserServiceTest {
    private static final Logger LOG = Logger.getLogger(UserServiceTest.class.getCanonicalName());
    @Inject
    private TransactionManager transactionManager;

    @Inject
    private UserService userService;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    @AfterEach
    void afterTest() throws SystemException {
        final int status = transactionManager.getStatus();
        if(status != Status.STATUS_NO_TRANSACTION) {
            LOG.warning("TX status is " + status + ", rolling back");
            transactionManager.rollback();
        } else {
            LOG.fine("TX status is " + status);
        }
    }

    @Test
    void assertPersistenceContextInjected() {
        assertNotNull(entityManager);
    }

    @Test
    void assertUserTransactionInjected() {
        assertNotNull(userTransaction);
    }

    @Test
    void storeUser() throws Exception {
        userTransaction.begin();
        UserEntity userEntity = new UserEntity();
        UserEntity userEntityTwo = new UserEntity();
        userService.storeUser(userEntity);
        userService.storeUser(userEntityTwo);
        userTransaction.commit();

        long id = userEntityTwo.getId();
        assertNotNull(userService.loadUser(id));
    }

    @Test
    void storeUserRollback() throws Exception {
        userTransaction.begin();
        UserEntity userEntity = new UserEntity();
        userService.storeUser(userEntity);
        userTransaction.rollback();

        long id = userEntity.getId();
        assertNull(userService.loadUser(id));
    }

    @Test
    void storeUserNewTransaction() throws Exception {
        userTransaction.begin();
        UserEntity userEntity = new UserEntity();
        userService.storeUserInNewTransaction(userEntity);
        userTransaction.rollback();

        long id = userEntity.getId();
        assertNotNull(userService.loadUser(id));
    }
    @Test
    void storeUserNewTransactionDontRollback() throws Exception {
        userTransaction.begin();
        UserEntity userEntity = new UserEntity();
        userService.storeUserInNewTransaction(userEntity);

        long id = userEntity.getId();
        assertNotNull(userService.loadUser(id));
    }

}
