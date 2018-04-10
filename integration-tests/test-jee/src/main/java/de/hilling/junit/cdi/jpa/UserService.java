package de.hilling.junit.cdi.jpa;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class UserService {

    @Inject
    private EntityManager entityManager;

    public long addUser(UserEntity userEntity) {
        entityManager.persist(userEntity);
        return userEntity.getId();
    }
}
