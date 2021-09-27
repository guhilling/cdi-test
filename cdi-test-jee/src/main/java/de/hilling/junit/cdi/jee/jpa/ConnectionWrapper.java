package de.hilling.junit.cdi.jee.jpa;

import java.sql.SQLException;

/**
 * Interface for classes providing wrapped connections.
 */
public interface ConnectionWrapper {

    /**
     * Execute database cleaner.
     *
     * @throws SQLException on any error during execution.
     */
    void callDatabaseCleaner() throws SQLException;
}
