package de.hilling.junit.cdi.jee.jpa.eclipselink;

import java.sql.SQLException;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.hilling.junit.cdi.CdiUnitRunner;
import de.hilling.junit.cdi.jee.jpa.DatabaseCleaner;

@RunWith(CdiUnitRunner.class)
public class EclipselinkConnectionWrapperTest {

    @Inject
    private EclipselinkConnectionWrapper connectionWrapper;
    @Inject
    private Instance<DatabaseCleaner> cleaner;

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("cdi-test-unit-eclipselink");
    }

    @Test
    public void runWithHibernatePersistence() throws SQLException {
        Assert.assertFalse(connectionWrapper.runWithConnection());
    }

    @Test
    public void runWithEclipseLinkPersistence() throws SQLException {
        connectionWrapper = new EclipselinkConnectionWrapper(entityManagerFactory.createEntityManager(), cleaner);
        Assert.assertTrue(connectionWrapper.runWithConnection());
    }
}