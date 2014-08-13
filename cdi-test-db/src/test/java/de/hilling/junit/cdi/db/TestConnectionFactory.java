package de.hilling.junit.cdi.db;

import de.hilling.junit.cdi.scope.TestSuiteScoped;
import org.junit.Assert;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

@TestSuiteScoped
public class TestConnectionFactory {
    private Connection testConnection;

    @PostConstruct
    protected void init() {
        testConnection = create();
    }

    private static Connection create() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.load(DbTestAbstract.class.getResourceAsStream("/properties/db.properties"));
            String url = dbProperties.getProperty("testdb.url");
            String user = dbProperties.getProperty("testdb.user");
            String password = dbProperties.getProperty("testdb.password");
            return getConnection(url, user, password);
        } catch (IOException | SQLException e) {
            Assert.fail(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Produces
    @Cleanup
    public Connection provideCleanupDataSource() {
        return testConnection;
    }

}
