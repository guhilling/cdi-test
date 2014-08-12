package de.hilling.junit.cdi.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionUtil {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void execute(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
