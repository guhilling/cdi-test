package de.hilling.junit.cdi.jee;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
class UserEntityTest {
    @Inject
    private EntityManager entityManager;

    @Inject
    private UserService userService;

    @Test
    void storeUserEntity() {
        UserEntity user = new UserEntity();
        Assertions.assertNotSame(user, entityManager.merge(user));
    }

    @Test
    void storeCustomerEntityTransactional() {
        UserEntity user = new UserEntity();
        userService.storeUser(user);
        Assertions.assertSame(user, entityManager.merge(user));
    }

    @Test
    void rollbackTransaction() {
        storeUserEntity();
        entityManager.getTransaction().setRollbackOnly();
    }
}
