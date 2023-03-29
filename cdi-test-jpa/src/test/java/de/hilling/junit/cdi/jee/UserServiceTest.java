package de.hilling.junit.cdi.jee;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transactional;
import jakarta.transaction.UserTransaction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
@Transactional(Transactional.TxType.REQUIRES_NEW)
class UserServiceTest {
    @Inject
    private UserService userService;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    @Test
    void assertPersistenceContextInjected() {
        assertNotNull(entityManager);
    }

    @Test
    void assertUserTransactionInjected() {
        assertNotNull(userTransaction);
    }

    @Transactional(Transactional.TxType.NEVER)
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
    void storeUserTestCaseTransaction() throws SystemException, NotSupportedException {
        UserEntity userEntity = new UserEntity();
        userService.storeUser(userEntity);
        long id = userEntity.getId();
        assertNotNull(userService.loadUser(id));
    }

    @Transactional(Transactional.TxType.NEVER)
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
    void storeUserRollbackIllegalWhenTransactional() {
        UserEntity userEntity = new UserEntity();
        userService.storeUserInNewTransaction(userEntity);
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> userTransaction.rollback());
        assertThat(illegalStateException.getMessage(), equalTo("BaseTransaction.rollback - ARJUNA016074: no transaction!"));
    }

    @Transactional(Transactional.TxType.NEVER)
    @Test
    void storeUserNewTransactionDontRollback() {
        UserEntity userEntity = new UserEntity();
        userService.storeUserInNewTransaction(userEntity);

        long id = userEntity.getId();
        assertNotNull(userService.loadUser(id));
    }

}
