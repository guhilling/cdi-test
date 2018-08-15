package de.hilling.junit.cdi.jee;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
public class UserEntityTest {
    @Inject
    private EntityManager entityManager;

    @Test
    public void storeUserEntity() {
        UserEntity user = new UserEntity();
        entityManager.persist(user);
    }
}
