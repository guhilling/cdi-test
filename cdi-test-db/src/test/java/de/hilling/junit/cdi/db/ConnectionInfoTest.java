package de.hilling.junit.cdi.db;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionInfoTest {
    private static int index;

    private Connection connection;
    private ConnectionInfo connectionInfo;

    @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:db-" + index);
        index++;
        connectionInfo = new ConnectionInfo();
    }


    @Test
    public void testParse() throws Exception {
        connectionInfo.parse(connection);
    }
}