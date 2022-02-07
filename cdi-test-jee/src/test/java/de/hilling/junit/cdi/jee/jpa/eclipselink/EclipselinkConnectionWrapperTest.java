package de.hilling.junit.cdi.jee.jpa.eclipselink;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.junit.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
class EclipselinkConnectionWrapperTest {

    @Inject
    private EclipselinkConnectionWrapper connectionWrapper;
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("cdi-test-unit-eclipselink");
    }

    @Test
    void runWithHibernatePersistence() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Assertions.assertNotNull(entityManager);
        connectionWrapper.callDatabaseCleaner(entityManager);
    }

    @Test
    void runWithEclipseLinkPersistence() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Assertions.assertNotNull(entityManager);
        connectionWrapper.callDatabaseCleaner(entityManager);
    }
}