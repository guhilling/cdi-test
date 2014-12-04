package de.hilling.junit.cdi.db;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionCleanerTest extends DbTestAbstract {

    private ConnectionCleaner cleaner;
    private ConnectionInfo info;

    @Inject
    @Cleanup
    private Connection testConnection;

    @Before
    public void setUp() throws SQLException, IOException {
        String sql = IOUtils.toString(getClass().getResourceAsStream("/properties/test-db.sql"));
        statement().execute(sql);
        info = new ConnectionInfo(testConnection);
        cleaner = new ConnectionCleaner(info);
    }

    @After
    public void tearDown() throws SQLException {
        testConnection.close();
    }

    @Test
    public void testCleanUp() throws Exception {
        Assert.assertEquals(1, countRows("Person"));
        cleaner.cleanUp();
        Assert.assertEquals(0, countRows("Person"));
    }

    private int countRows(String table) throws SQLException {
        ResultSet result = statement().executeQuery("select count(*) from " + table);
        result.next();
        return result.getInt(1);
    }

    private Statement statement() throws SQLException {
        return testConnection.createStatement();
    }
}