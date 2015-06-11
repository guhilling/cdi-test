package de.hilling.cdi.sampleapp;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EntityManagerFactory {

    @PersistenceContext
    private EntityManager entityManager;

    @Produces
    public EntityManager provideEntityManager() {
        return entityManager;
    }
}
