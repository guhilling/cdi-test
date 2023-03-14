package de.hilling.junit.cdi.jee.jpa;

import jakarta.persistence.EntityManager;

import java.util.logging.Logger;

import org.jboss.weld.injection.spi.ResourceReference;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;

import de.hilling.junit.cdi.ContextControlWrapper;

public class EntityManagerResourceFactory implements ResourceReferenceFactory<EntityManager> {
    private static final Logger LOG = Logger.getLogger(EntityManagerResourceFactory.class.getCanonicalName());

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
            EntityManager entityManager;

            @Override
            public EntityManager getInstance() {
                entityManager = testEntityResources.resolveEntityManager(persistenceUnit);
                return entityManager;
            }
            @Override
            public void release() {
                LOG.finest("closing EntityManager done by TestEntityResources");
            }
        };
    }
}
