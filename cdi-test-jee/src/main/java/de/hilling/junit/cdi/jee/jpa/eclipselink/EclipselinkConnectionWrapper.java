package de.hilling.junit.cdi.jee.jpa.eclipselink;

import java.sql.Connection;
import java.sql.SQLException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import de.hilling.junit.cdi.jee.jpa.ConnectionWrapper;
import de.hilling.junit.cdi.jee.jpa.DatabaseCleaner;

@RequestScoped
public class EclipselinkConnectionWrapper implements ConnectionWrapper {

    private Instance<DatabaseCleaner> cleaner;
    private EntityManager             entityManager;

    /**
     * make it proxyable.
     */
    public EclipselinkConnectionWrapper() {
    }

    @Inject
    public EclipselinkConnectionWrapper(EntityManager entityManager, Instance<DatabaseCleaner> cleaner) {
        this.entityManager = entityManager;
        this.cleaner = cleaner;
    }

    @Override
    public boolean callDatabaseCleaner() throws SQLException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Connection connection = (Connection) entityManager.unwrap(Connection.class);
            if (connection == null) {
                transaction.rollback();
                return false;
            } else {
                if (!cleaner.isUnsatisfied()) {
                    cleaner.get().run(connection);
                }
                transaction.commit();
                return true;
            }
        } catch (RuntimeException re) {
            transaction.rollback();
            return false;
        }
    }
}
