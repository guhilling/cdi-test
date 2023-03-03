package de.hilling.junit.cdi.jee.jpa;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Hook for database cleanup.
 *
 * <p>
 *     If a bean for this interface is found it will be run before the tests.
 * </p>
 */
public interface DatabaseCleaner {

    /**
     * Cleanup the database before test.
     *
     * @param connection SQL connection used for jpa.
     * @throws SQLException exception thrown during execution.
     */
    void run(Connection connection) throws SQLException;
}
