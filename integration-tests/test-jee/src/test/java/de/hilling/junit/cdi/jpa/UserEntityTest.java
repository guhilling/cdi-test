package de.hilling.junit.cdi.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
public class UserEntityTest {
    @Inject
    private EntityManager entityManager;
    @Inject
    private TestUtils testUtils;

    @Test
    public void storeUserEntityWithNullAttributes() {
        UserEntity userEntity = new UserEntity();
        Assertions.assertThrows(PersistenceException.class, () -> entityManager.persist(userEntity));
    }

    @Test
    public void storeUserEntity() {
        entityManager.persist(testUtils.createGunnar());
    }
}
