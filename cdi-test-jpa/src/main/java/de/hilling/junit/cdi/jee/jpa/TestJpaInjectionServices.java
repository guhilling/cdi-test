package de.hilling.junit.cdi.jee.jpa;

import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import org.jboss.weld.injection.spi.JpaInjectionServices;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;

import de.hilling.junit.cdi.ContextControlWrapper;

public class TestJpaInjectionServices implements JpaInjectionServices {

    private final ContextControlWrapper contextControl = ContextControlWrapper.getInstance();

    private EntityManagerResourceFactory entityManagerResourceFactory = new EntityManagerResourceFactory();
    private EntityManagerFactoryResourceFactory entityManagerFactoryResourceFactory = new EntityManagerFactoryResourceFactory();

    @Override
    public ResourceReferenceFactory<EntityManager> registerPersistenceContextInjectionPoint(
    InjectionPoint injectionPoint) {
        return entityManagerResourceFactory;
    }

    @Override
    public ResourceReferenceFactory<EntityManagerFactory> registerPersistenceUnitInjectionPoint(
    InjectionPoint injectionPoint) {
        return entityManagerFactoryResourceFactory;
    }

    @Override
    public void cleanup() {
    }
}
