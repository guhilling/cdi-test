package de.hilling.junit.cdi.jee.jpa.eclipselink;

import java.sql.Connection;
import java.sql.SQLException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import de.hilling.junit.cdi.CdiTestException;
import de.hilling.junit.cdi.jee.jpa.ConnectionWrapper;
import de.hilling.junit.cdi.jee.jpa.DatabaseCleaner;

/**
 * Wrapper for Eclipse Link to call {@link DatabaseCleaner}.
 */
@RequestScoped
public class EclipselinkConnectionWrapper implements ConnectionWrapper {

    @Inject
    private Instance<DatabaseCleaner> cleaner;

    @Override
    public void callDatabaseCleaner(EntityManager entityManager) {
        cleanEntityManager(entityManager);
    }

    private void cleanEntityManager(EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Connection connection = entityManager.unwrap(Connection.class);
            if (connection == null) {
                transaction.rollback();
            } else {
                cleanUpDatabase(connection);
                transaction.commit();
            }
        } catch (RuntimeException re) {
            transaction.rollback();
        }
    }

    private void cleanUpDatabase(Connection connection) {
        if (!cleaner.isUnsatisfied()) {
            try {
                cleaner.get().run(connection);
            } catch (SQLException e) {
                throw new CdiTestException("cleaning database failed", e);
            }
        }
    }
}
