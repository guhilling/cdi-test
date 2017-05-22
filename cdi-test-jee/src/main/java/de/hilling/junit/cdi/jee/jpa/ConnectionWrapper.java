package de.hilling.junit.cdi.jee.jpa;

import java.sql.SQLException;

/**
 * Interface for classes providing wrapped connections.
 */
public interface ConnectionWrapper {

    /**
     * Execute specified code in connection.
     * @throws SQLException on any error during execution.
     */
    boolean runWithConnection() throws SQLException;
}
