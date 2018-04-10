package de.hilling.junit.cdi.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.junit.Test;
import org.junit.runner.RunWith;

import de.hilling.junit.cdi.CdiUnitRunner;

@RunWith(CdiUnitRunner.class)
public class UserEntityTest {
    @Inject
    private EntityManager entityManager;
    @Inject
    private TestUtils testUtils;

    @Test(expected = PersistenceException.class)
    public void storeUserEntityWithNullAttributes() {
        UserEntity userEntity = new UserEntity();
        entityManager.persist(userEntity);
    }

    @Test
    public void storeUserEntity() {
        entityManager.persist(testUtils.createGunnar());
    }
}
