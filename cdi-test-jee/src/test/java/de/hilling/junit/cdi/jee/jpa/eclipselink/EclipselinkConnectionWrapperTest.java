package de.hilling.junit.cdi.jee.jpa.eclipselink;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;

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