package de.hilling.junit.cdi.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class GenericConstraintDisablerTest {


    private Connection connection;
    private ConnectionUtil connectionUtil;
    private GenericConstraintDisabler disabler;

    @Before
    public void setUp() {
        connection = H2ConnectionCreator.create();
        connectionUtil = new ConnectionUtil();
        connectionUtil.setConnection(connection);
        connectionUtil.execute("CREATE TABLE person(" +
                "fk_name VARCHAR(32)," +
                "id INTEGER," +
                "parent INTEGER," +
                "PRIMARY KEY(id)," +
                "FOREIGN KEY(parent) REFERENCES person(id)" +
                ");");
        disabler = new GenericConstraintDisabler(new ConnectionInfo(connection));
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test(expected = RuntimeException.class)
    public void insertFails()   {
         connectionUtil.execute("INSERT INTO person VALUES ('gunnar', 0, 1)");
    }

    @Test
    public void testDisableConstraints()  {
        disabler.disableConstraints();
        connectionUtil.execute("INSERT INTO person VALUES ('gunnar', 0, 1)");
    }

    @Test(expected = RuntimeException.class)
    public void testReEnableConstraints()  {
        disabler.disableConstraints();
        disabler.enableConstraints();
        connectionUtil.execute("INSERT INTO person VALUES ('gunnar', 0, 1)");
    }
}