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
    private ConnectionUtil util;

    @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:db-" + index);
        util = new ConnectionUtil();
        util.setConnection(connection);
        util.execute("CREATE TABLE SAMPLE\n" +
                "(\n" +
                "   `ID`              varchar(63),\n" +
                "   `DESCRIPTION`     varchar(255),\n" +
                ")");
        util.execute("INSERT INTO SAMPLE VALUES('one', 'description-1')");
        util.execute("INSERT INTO SAMPLE VALUES('one', 'description-2')");
        index++;
        connectionInfo = new ConnectionInfo(connection);
    }


    @Test
    public void testDeleteTable() {

    }
}