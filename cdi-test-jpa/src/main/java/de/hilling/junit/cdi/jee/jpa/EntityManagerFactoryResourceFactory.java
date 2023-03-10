package de.hilling.junit.cdi.jee.jpa;

import jakarta.persistence.EntityManagerFactory;

import org.jboss.weld.injection.spi.ResourceReference;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;

import de.hilling.junit.cdi.ContextControlWrapper;

public class EntityManagerFactoryResourceFactory implements ResourceReferenceFactory<EntityManagerFactory> {

    private TestEntityResources testEntityResources;
    private final String persistenceUnit;

    public EntityManagerFactoryResourceFactory(String persistenceUnit) {
        this.persistenceUnit = persistenceUnit;
    }

    @Override
    public ResourceReference<EntityManagerFactory> createResource() {
        if(testEntityResources==null) {
            testEntityResources = ContextControlWrapper.getInstance().getContextualReference(TestEntityResources.class);
        }
        return new ResourceReference<>() {
            @Override
            public EntityManagerFactory getInstance() {
                return testEntityResources.resolveEntityManagerFactory(persistenceUnit);
            }
            @Override
            public void release() {
                // done otherwise
            }
        };
    }
}
