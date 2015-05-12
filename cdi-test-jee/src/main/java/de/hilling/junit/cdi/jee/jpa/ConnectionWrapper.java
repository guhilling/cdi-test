package de.hilling.junit.cdi.jee.jpa;

import java.sql.SQLException;

/**
 * Interface for classes providing wrapped connections.
 */
public interface ConnectionWrapper {
    void runWithConnection(Work work) throws SQLException;
}
