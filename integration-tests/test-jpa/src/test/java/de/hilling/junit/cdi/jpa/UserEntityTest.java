package de.hilling.junit.cdi.jpa;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
class UserEntityTest {
    @Inject
    private EntityManager     entityManager;
    @Inject
    private EntitySupport     entitySupport;
    @Inject
    private InvocationCounter counter;

    @Test
    void storeUserEntityWithNullAttributes() {
        UserEntity userEntity = new UserEntity();
        Assertions.assertThrows(PersistenceException.class, () -> entityManager.persist(userEntity));
    }

    @Test
    void storeUserEntity() {
        entityManager.persist(entitySupport.createGunnar());
        Assertions.assertEquals(1, counter.get());
    }

    @Test
    void storeAndRemoveUserEntity() {
        UserEntity gunnar = entitySupport.createGunnar();
        entityManager.persist(gunnar);
        entityManager.remove(gunnar);
        Assertions.assertEquals(2, counter.get());
    }
}
