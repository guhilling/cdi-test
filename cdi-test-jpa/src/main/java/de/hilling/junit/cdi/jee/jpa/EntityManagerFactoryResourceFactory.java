package de.hilling.junit.cdi.jee.jpa;

import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;

import org.jboss.weld.injection.spi.ResourceReference;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;

import de.hilling.junit.cdi.scope.TestSuiteScoped;

@TestSuiteScoped
public class EntityManagerFactoryResourceFactory implements ResourceReferenceFactory<EntityManagerFactory> {

    @Inject
    private TestEntityResources testEntityResources;

    @Inject
    private BeanManager beanManager;

    @Override
    public ResourceReference<EntityManagerFactory> createResource() {
        return null;
    }
}
