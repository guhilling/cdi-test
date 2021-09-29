package de.hilling.junit.cdi.jee.jpa.eclipselink;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
        connectionWrapper.callDatabaseCleaner(entityManagerFactory.createEntityManager());
    }

    @Test
    void runWithEclipseLinkPersistence() {
        connectionWrapper.callDatabaseCleaner(entityManagerFactory.createEntityManager());
    }
}