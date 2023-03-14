package de.hilling.junit.cdi.jee.jpa;

import jakarta.persistence.EntityManagerFactory;

import java.util.logging.Logger;

import org.jboss.weld.injection.spi.ResourceReference;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;

import de.hilling.junit.cdi.ContextControlWrapper;

public class EntityManagerFactoryResourceFactory implements ResourceReferenceFactory<EntityManagerFactory> {
    private static final Logger LOG = Logger.getLogger(EntityManagerFactoryResourceFactory.class.getCanonicalName());

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
            EntityManagerFactory emf;

            @Override
            public EntityManagerFactory getInstance() {
                emf = testEntityResources.resolveEntityManagerFactory(persistenceUnit);
                return emf;
            }
            @Override
            public void release() {
                LOG.fine("closing EntityManagerFactory" + emf);
                emf.close();
            }
        };
    }
}
