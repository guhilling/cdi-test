package de.hilling.cdi.sampleapp;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.hilling.junit.cdi.jee.jpa.DatabaseCleaner;

/**
 * Cleanup database.
 *
 * <p>
 * Brute force implementation.
 * </p>
 */
public class CustomDatabaseCleaner implements DatabaseCleaner {

    public static final String USER_TABLE_IDENTIFIER = "TABLE";

    public void run(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(null, null, null, new String[]{USER_TABLE_IDENTIFIER});
        while (tables.next()) {
            String tableName = tables.getString(3);
            if (!tableName.equals("SEQUENCE")) {
                connection.prepareStatement("delete from " + tableName).execute();
            }
        }
        connection.commit();
    }

}
