package de.hilling.junit.cdi.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
class UserEntityTest {
    @Inject
    private EntityManager entityManager;
    @Inject
    private TestUtils testUtils;
    @Inject
    private InvocationCounter counter;

    @Test
    void storeUserEntityWithNullAttributes() {
        UserEntity userEntity = new UserEntity();
        Assertions.assertThrows(PersistenceException.class, () -> entityManager.persist(userEntity));
    }

    @Test
    void storeUserEntity() {
        entityManager.persist(testUtils.createGunnar());
        Assertions.assertEquals(1, counter.get());
    }

    @Test
    void storeAndRemoveUserEntity() {
        UserEntity gunnar = testUtils.createGunnar();
        entityManager.persist(gunnar);
        entityManager.remove(gunnar);
        Assertions.assertEquals(2, counter.get());
    }
}
