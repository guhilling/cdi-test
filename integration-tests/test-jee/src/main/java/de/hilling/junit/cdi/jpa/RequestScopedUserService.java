package de.hilling.junit.cdi.jpa;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@RequestScoped
public class RequestScopedUserService {

    @Inject
    private EntityManager entityManager;

    public long addUser(UserEntity userEntity) {
        entityManager.persist(userEntity);
        return userEntity.getId();
    }
}
