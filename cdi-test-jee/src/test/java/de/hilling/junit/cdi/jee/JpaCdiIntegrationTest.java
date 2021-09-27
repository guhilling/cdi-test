package de.hilling.junit.cdi.jee;

import de.hilling.junit.cdi.CdiTestJunitExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@ExtendWith(CdiTestJunitExtension.class)
public class JpaCdiIntegrationTest {
    @Inject
    private EntityManager entityManager;
    @Inject
    private EntityManager entityManagerB;

    @Inject
    private UpdateCounter updateCounter;

    @Test
    public void storeUserEntity() {
        UserEntity user = new UserEntity();
        entityManager.persist(user);
        Assertions.assertEquals(1, updateCounter.get());
    }

    @Test
    public void storeAndDeleteUserEntity() {
        UserEntity user = new UserEntity();
        entityManager.persist(user);
        entityManager.remove(user);
        Assertions.assertEquals(2, updateCounter.get());
    }

}
