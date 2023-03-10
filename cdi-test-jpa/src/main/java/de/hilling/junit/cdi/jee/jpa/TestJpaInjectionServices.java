package de.hilling.junit.cdi.jee.jpa;

import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;

import org.jboss.weld.injection.spi.JpaInjectionServices;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;

import de.hilling.junit.cdi.CdiTestException;

public class TestJpaInjectionServices implements JpaInjectionServices {
    public static final String DEFAULT_TEST_PERSISTENCE_UNIT = "cdi-test";

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

    @Override
    public ResourceReferenceFactory<EntityManagerFactory> registerPersistenceUnitInjectionPoint(
    InjectionPoint injectionPoint) {
        PersistenceUnit persistenceUnit = injectionPoint.getAnnotated().getAnnotation(PersistenceUnit.class);
        if(persistenceUnit==null) {
            throw new CdiTestException("no @PersistenceUnit annotation found on injection point " + injectionPoint);
        }
        String unitName = resolveUnitName(persistenceUnit);
        return new EntityManagerFactoryResourceFactory(unitName);
    }

    private String resolveUnitName(PersistenceUnit persistenceUnit) {
        if(!persistenceUnit.unitName().isEmpty()) {
            return persistenceUnit.unitName();
        } else {
            return DEFAULT_TEST_PERSISTENCE_UNIT;
        }
    }

    private static String resolveUnitName(PersistenceContext persistenceContext) {
        if(!persistenceContext.unitName().isEmpty()) {
            return persistenceContext.unitName();
        } else {
            return DEFAULT_TEST_PERSISTENCE_UNIT;
        }
    }

    @Override
    public void cleanup() {
        // not needed
    }
}
