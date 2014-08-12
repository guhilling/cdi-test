package de.hilling.junit.cdi.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionInfo {
    private DatabaseType type;

    public void parse(Connection connection) {
        try {
            final DatabaseMetaData metaData = connection.getMetaData();
            type = DatabaseType.byType(metaData.getDatabaseProductName());
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                tables.getString("TABLE_NAME");
            }
        } catch (SQLException e) {
            throw new RuntimeException("unable to create connection info", e);
        }
    }
}
