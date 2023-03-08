package de.hilling.junit.cdi.jee.jpa;

import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;

import org.jboss.weld.injection.spi.JpaInjectionServices;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;

import de.hilling.junit.cdi.CdiTestException;

public class TestJpaInjectionServices implements JpaInjectionServices {
    public static final String DEFAULT_TEST_PERSISTENCE_UNIT = "cdi-test";

    private EntityManagerFactoryResourceFactory entityManagerFactoryResourceFactory = new EntityManagerFactoryResourceFactory();

    @Override
    public ResourceReferenceFactory<EntityManager> registerPersistenceContextInjectionPoint(
    InjectionPoint injectionPoint) {
        PersistenceContext persistenceContext = injectionPoint.getAnnotated().getAnnotation(PersistenceContext.class);
        if(persistenceContext==null) {
            throw new CdiTestException("no @PersistenceContext annotation found on injection point " + injectionPoint);
        }
        String persistenceUnit = resolveUnitName(persistenceContext);
        return new EntityManagerResourceFactory(persistenceUnit);
    }

    private static String resolveUnitName(PersistenceContext persistenceContext) {
        if(!persistenceContext.unitName().isEmpty()) {
            return persistenceContext.unitName();
        } else {
            return DEFAULT_TEST_PERSISTENCE_UNIT;
        }
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
