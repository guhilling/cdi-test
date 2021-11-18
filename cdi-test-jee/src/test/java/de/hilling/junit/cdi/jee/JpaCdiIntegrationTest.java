package de.hilling.junit.cdi.jee;

import de.hilling.junit.cdi.CdiTestJunitExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ExtendWith(CdiTestJunitExtension.class)
class JpaCdiIntegrationTest {
    @Inject
    private EntityManager entityManager;

    @Inject
    @SecondEntityManager
    private EntityManager entityManagerB;

    @Inject
    private UpdateCounter updateCounter;

    @Test
    void storeUserEntity() {
        UserEntity user = new UserEntity();
        entityManager.persist(user);
        Assertions.assertEquals(1, updateCounter.get());
    }

    @Test
    void storeCustomerEntity() {
        CustomerEntity customer = new CustomerEntity();
        entityManagerB.persist(customer);
        Assertions.assertEquals(1, updateCounter.get());
    }

    @Test
    void storeAndDeleteUserEntity() {
        UserEntity user = new UserEntity();
        entityManager.persist(user);
        entityManager.remove(user);
        Assertions.assertEquals(2, updateCounter.get());
    }

}
