package de.hilling.junit.cdi.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ConnectionInfoTest {

    private ConnectionInfo connectionInfo;
    private ConnectionUtil util = new ConnectionUtil();

    @Before
    public void setUp() throws SQLException {
        Connection connection = H2ConnectionCreator.create();
        connectionInfo = new ConnectionInfo(connection);
        util.setConnection(connection);
    }

    @After
    public void tearDown() throws SQLException {
        connectionInfo.getConnection().close();
    }

    @Test
    public void testDeleteTableContents() {
        assertNotEquals(0, util.countRows("SAMPLE"));
        new ConnectionCleaner(connectionInfo).cleanUp();
        assertEquals(0, util.countRows("SAMPLE"));
    }

    @Test
    public void testDeleteTableContentsSampleDb() {
        assertNotEquals(0, util.countRows("SAMPLE"));
        new ConnectionCleaner(connectionInfo).cleanUp();
        assertEquals(0, util.countRows("SAMPLE"));
    }
}