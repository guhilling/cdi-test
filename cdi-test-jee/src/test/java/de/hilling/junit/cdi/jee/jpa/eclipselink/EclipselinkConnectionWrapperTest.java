package de.hilling.junit.cdi.jee.jpa.eclipselink;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;
import de.hilling.junit.cdi.jee.jpa.DatabaseCleaner;

@ExtendWith(CdiTestJunitExtension.class)
public class EclipselinkConnectionWrapperTest {

    @Inject
    private EclipselinkConnectionWrapper connectionWrapper;
    @Inject
    private Instance<DatabaseCleaner> cleaner;

    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("cdi-test-unit-eclipselink");
    }

    @Test
    public void runWithHibernatePersistence() throws SQLException {
        assertFalse(connectionWrapper.runWithConnection());
    }

    @Test
    public void runWithEclipseLinkPersistence() throws SQLException {
        connectionWrapper = new EclipselinkConnectionWrapper(entityManagerFactory.createEntityManager(), cleaner);
        assertTrue(connectionWrapper.runWithConnection());
    }
}