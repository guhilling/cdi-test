package de.hilling.junit.cdi.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionInfo {
    private final DatabaseType type;

    private final Connection connection;
    private final List<String> tableNames = new ArrayList<>();

    public ConnectionInfo(Connection connection) {
        this.connection = connection;
        try {
            final DatabaseMetaData metaData = connection.getMetaData();
            type = DatabaseType.byType(metaData.getDatabaseProductName());
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                tableNames.add(tables.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("unable to create connection info", e);
        }
    }

    public DatabaseType getType() {
        return type;
    }

    public Connection getConnection() {
        return connection;
    }

    public List<String> getTableNames() {
        return tableNames;
    }


}
