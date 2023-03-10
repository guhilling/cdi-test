package de.hilling.junit.cdi.jee.jpa;

import jakarta.persistence.EntityManager;

import org.jboss.weld.injection.spi.ResourceReference;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;

import de.hilling.junit.cdi.ContextControlWrapper;

public class EntityManagerResourceFactory implements ResourceReferenceFactory<EntityManager> {
    private TestEntityResources testEntityResources;
    private final String persistenceUnit;

    public EntityManagerResourceFactory(String persistenceUnit) {
        this.persistenceUnit = persistenceUnit;
    }

    @Override
    public ResourceReference<EntityManager> createResource() {
        if(testEntityResources==null) {
            testEntityResources = ContextControlWrapper.getInstance().getContextualReference(TestEntityResources.class);
        }
        return new ResourceReference<>() {
            @Override
            public EntityManager getInstance() {
                return testEntityResources.resolveEntityManager(persistenceUnit);
            }
            @Override
            public void release() {
                // done otherwise
            }
        };
    }
}
