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
    private final Map<String, EntityManagerFactory> factories = new HashMap<>();

    @Override
    public ResourceReference<EntityManager> createResource() {
        return new ResourceReference<EntityManager>() {

            private EntityManager entityManager;

            @Override
            public EntityManager getInstance() {
                entityManager = createEntityManagerFactory("cdi-test").createEntityManager();
                return entityManager;
            }

            @Override
            public void release() {
                entityManager.close();
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
