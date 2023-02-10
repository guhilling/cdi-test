package de.hilling.junit.cdi.jee;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {
    @Inject
    private EntityManager entityManager;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void storeUser(UserEntity userEntity) {
        entityManager.persist(userEntity);
    }
}
