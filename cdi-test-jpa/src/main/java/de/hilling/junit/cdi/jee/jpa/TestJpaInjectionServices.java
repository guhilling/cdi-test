package de.hilling.junit.cdi.jee.jpa;

import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;

import org.jboss.weld.injection.spi.JpaInjectionServices;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;

import de.hilling.junit.cdi.CdiTestException;

public class TestJpaInjectionServices implements JpaInjectionServices {

    private EntityManagerResourceFactory entityManagerResourceFactory = new EntityManagerResourceFactory();
    private EntityManagerFactoryResourceFactory entityManagerFactoryResourceFactory = new EntityManagerFactoryResourceFactory();

    @Override
    public ResourceReferenceFactory<EntityManager> registerPersistenceContextInjectionPoint(
    InjectionPoint injectionPoint) {
        PersistenceContext persistenceContext = injectionPoint.getAnnotated().getAnnotation(PersistenceContext.class);
        if(persistenceContext==null) {
            throw new CdiTestException("no @PersistenceContext annotation found on injection point " + injectionPoint);
        }
        String persistenceUnit = persistenceContext.unitName();
        return entityManagerResourceFactory;
    }

    @Override
    public ResourceReferenceFactory<EntityManagerFactory> registerPersistenceUnitInjectionPoint(
    InjectionPoint injectionPoint) {
        return entityManagerFactoryResourceFactory;
    }

    @Override
    public void cleanup() {
        // not needed
    }
}
