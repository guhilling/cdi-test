package de.hilling.junit.cdi.jee;

import de.hilling.junit.cdi.annotations.GlobalTestImplementation;
import de.hilling.junit.cdi.scope.TestSuiteScoped;
import org.mockito.Mockito;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * Producer for EntityManagers used in cdi-test unit tests.
 */
@TestSuiteScoped
public class EntityManagerTestProducer {
    private EntityManagerFactory entityManagerFactory;

    @Inject
    private BeanManager beanManager;

    @Inject
    private Instance<JEETestConfiguration> configuration;

    @PostConstruct
    protected void createEntityManagerFactory() {
        if(configuration.isResolvable()) {
            Map<String, Object> props = new HashMap<>();
            props.put("javax.persistence.bean.manager", beanManager);
            entityManagerFactory = Persistence.createEntityManagerFactory(configuration.get().getTestPersistenceUnitName(), props);
        }
    }

    @Produces
    @GlobalTestImplementation
    @RequestScoped
    protected EntityManagerFactory provideTestEntityManagerFactory() {
        return entityManagerFactory;
    }

    @Produces
    @GlobalTestImplementation
    @RequestScoped
    protected EntityManager provideTestEntityManager() {
        if(entityManagerFactory != null) {
            return entityManagerFactory.createEntityManager();
        } else {
            return Mockito.mock(EntityManager.class);
        }
    }

    public void close(@Disposes EntityManager entityManager) {
        entityManager.close();
    }
}
