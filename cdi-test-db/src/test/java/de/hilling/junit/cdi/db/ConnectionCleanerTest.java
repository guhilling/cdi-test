package de.hilling.junit.cdi.db;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionCleanerTest extends DbTestAbstract {

    private ConnectionCleaner cleaner;
    private ConnectionInfo info;

    @Inject
    private ConnectionUtil util;

    @Inject
    @Cleanup
    private Connection testConnection;

    @Before
    public void setUp() throws SQLException, IOException {
        util.setConnection(testConnection);
        String sql = IOUtils.toString(getClass().getResourceAsStream("/properties/test-db.sql"));
        util.execute(sql);
        info = new ConnectionInfo(testConnection);
        cleaner = new ConnectionCleaner(info);
    }

    @After
    public void tearDown() throws SQLException {
        testConnection.close();
    }

    @Test
    public void testCleanUp() throws Exception {
        Assert.assertEquals(1, util.countRows("Person"));
        cleaner.cleanUp();
        Assert.assertEquals(0, util.countRows("Person"));
    }

}