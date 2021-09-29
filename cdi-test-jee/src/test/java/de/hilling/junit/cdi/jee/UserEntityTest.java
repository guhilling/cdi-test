package de.hilling.junit.cdi.jee;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
public class UserEntityTest {
    @Inject
    private EntityManager entityManager;

    @Inject
    @SecondEntityManager
    private EntityManager entityManagerB;

    @Test
    public void storeUserEntity() {
        UserEntity user = new UserEntity();
        entityManager.persist(user);
    }

    @Test
    public void storeCustomerEntity() {
        CustomerEntity customer = new CustomerEntity();
        entityManagerB.persist(customer);
    }

    @Test
    public void storeBothEntitiesToDifferentDBs() {
        storeCustomerEntity();
        storeUserEntity();
    }

    @Test
    public void rollbackTransaction() {
        storeUserEntity();
        entityManager.getTransaction().setRollbackOnly();
    }
}
