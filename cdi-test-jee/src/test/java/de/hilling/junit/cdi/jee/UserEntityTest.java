package de.hilling.junit.cdi.jee;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
class UserEntityTest {
    @Inject
    private EntityManager entityManager;

    @Inject
    @SecondEntityManager
    private EntityManager entityManagerB;

    @Test
    void storeUserEntity() {
        UserEntity user = new UserEntity();
        entityManager.persist(user);
    }

    @Test
    void storeCustomerEntity() {
        CustomerEntity customer = new CustomerEntity();
        entityManagerB.persist(customer);
    }

    @Test
    void storeBothEntitiesToDifferentDBs() {
        storeCustomerEntity();
        storeUserEntity();
    }

    @Test
    void rollbackTransaction() {
        storeUserEntity();
        entityManager.getTransaction().setRollbackOnly();
    }
}
