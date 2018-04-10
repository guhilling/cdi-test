package de.hilling.junit.cdi.jpa;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class UserService {

    @Inject
    private EntityManager entityManager;

    public long addUser(User user) {
        final UserEntity userEntity = new UserEntity();
        userEntity.setBirthDate(user.getBirthDate());
        userEntity.setFirstName(user.getFirstName());
        entityManager.persist(userEntity);
        return userEntity.getId();
    }
}
