package de.hilling.junit.cdi.jee.jpa;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Cleanup database.
 */
public class DatabaseCleaner implements Work {

    public static final String USER_TABLE_IDENTIFIER = "TABLE";

    @Override
    public void run(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(null, null, null, new String[]{USER_TABLE_IDENTIFIER});
        while (tables.next()) {
            String tableName = tables.getString(3);
            connection.prepareStatement("delete from " + tableName).execute();
        }
        connection.commit();
    }
}
