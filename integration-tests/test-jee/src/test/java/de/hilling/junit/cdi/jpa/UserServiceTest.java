package de.hilling.junit.cdi.jpa;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
class UserServiceTest {

    @Inject
    private UserService   userService;
    @Inject
    private EntityManager entityManager;
    @Inject
    private EntitySupport testUtils;

    @Test
    void addUser() {
        long id = userService.addUser(testUtils.createGunnar());
        Assertions.assertNotNull(entityManager.find(UserEntity.class, id));
    }
}