package de.hilling.junit.cdi.jee.jpa.eclipselink;

import de.hilling.junit.cdi.CdiUnitRunner;
import de.hilling.junit.cdi.jee.jpa.DatabaseCleaner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;

@RunWith(CdiUnitRunner.class)
public class EclipselinkConnectionWrapperTest {

    @Inject
    private EclipselinkConnectionWrapper connectionWrapper;

    @Inject
    private DatabaseCleaner cleaner;
    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("cdi-test-unit-eclipselink");
    }

    @Test
    public void runWithHibernatePersistence() throws SQLException {
        Assert.assertFalse(connectionWrapper.runWithConnection(cleaner));
    }

    @Test
    public void runWithEclipseLinkPersistence() throws SQLException {
        connectionWrapper = new EclipselinkConnectionWrapper(entityManagerFactory.createEntityManager());
        Assert.assertTrue(connectionWrapper.runWithConnection(cleaner));
    }
}