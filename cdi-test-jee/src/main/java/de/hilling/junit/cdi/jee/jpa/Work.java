package de.hilling.junit.cdi.jee.jpa;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface for running code using a connection.
 */
public interface Work {
    /**
     * provide a connection within given method.
     * @param connection jdbc connection.
     */
    void run(Connection connection) throws SQLException;
}
