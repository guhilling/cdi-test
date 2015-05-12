package de.hilling.junit.cdi.jee.jpa;

import java.sql.SQLException;

/**
 * Interface for classes providing wrapped connections.
 */
public interface ConnectionWrapper {

    /**
     * Execute specified code in connection.
     * @param work
     * @throws SQLException
     */
    boolean runWithConnection(DatabaseCleaner work) throws SQLException;
}
