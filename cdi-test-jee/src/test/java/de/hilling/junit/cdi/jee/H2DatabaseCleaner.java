package de.hilling.junit.cdi.jee;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import de.hilling.junit.cdi.jee.jpa.DatabaseCleaner;

/**
 * Cleanup database.
 *
 * <p>
 * Brute force implementation.
 * </p>
 */
public class H2DatabaseCleaner implements DatabaseCleaner {

    public static final String USER_TABLE_IDENTIFIER = "TABLE";

    public void run(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(null, "PUBLIC", null, new String[]{USER_TABLE_IDENTIFIER});
        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME");
            if(!tableName.equals("SEQUENCE")) {
                connection.prepareStatement("delete from " + tableName).execute();
            }
        }
        connection.commit();
    }

}
