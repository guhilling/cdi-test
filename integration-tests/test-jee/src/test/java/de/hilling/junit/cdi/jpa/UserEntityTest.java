package de.hilling.junit.cdi.jpa;

import java.time.LocalDate;
import java.time.Month;

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

    @Test(expected = PersistenceException.class)
    public void storeUserEntityWithNullAttributes() {
        UserEntity userEntity = new UserEntity();
        entityManager.persist(userEntity);
    }

    @Test
    public void storeUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Gunnar");
        userEntity.setBirthDate(LocalDate.of(1971, Month.JUNE, 15));
        entityManager.persist(userEntity);
    }
}
