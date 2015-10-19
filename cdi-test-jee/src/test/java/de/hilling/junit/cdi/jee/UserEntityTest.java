package de.hilling.junit.cdi.jee;

import de.hilling.junit.cdi.CdiUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@RunWith(CdiUnitRunner.class)
public class UserEntityTest {
    @Inject
    private EntityManager entityManager;

    @Test
    public void storeUserEntity() {
        UserEntity user = new UserEntity();
        entityManager.persist(user);
    }
}
