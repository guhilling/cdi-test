package de.hilling.junit.cdi.jee.jpa;

import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

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
                if (!testEntityResources.hasEntityManager(persistenceUnit)) {
                    testEntityResources.putEntityManager(persistenceUnit,
                                                         createEntityManagerFactory(persistenceUnit).createEntityManager());
                }
                return testEntityResources.getEntityManager(persistenceUnit);
            }
            @Override
            public void release() {
                // done otherwise
            }
        };
    }

    private EntityManagerFactory createEntityManagerFactory(String persistenceUnit) {
        BeanManager beanManager = ContextControlWrapper.getInstance().getContextualReference(BeanManager.class);
        Map<String, Object> props = new HashMap<>();
        props.put("jakarta.persistence.bean.manager", beanManager);
        props.put("javax.persistence.bean.manager", beanManager);
        return Persistence.createEntityManagerFactory(persistenceUnit, props);
    }

}
