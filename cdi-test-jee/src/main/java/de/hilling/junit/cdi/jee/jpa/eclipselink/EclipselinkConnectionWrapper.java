package de.hilling.junit.cdi.jee.jpa.eclipselink;

import de.hilling.junit.cdi.jee.jpa.ConnectionWrapper;
import de.hilling.junit.cdi.jee.jpa.DatabaseCleaner;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.Connection;
import java.sql.SQLException;

@RequestScoped
public class EclipselinkConnectionWrapper implements ConnectionWrapper {

    @Inject
    private EntityManager entityManager;

    @Override
    public boolean runWithConnection(final DatabaseCleaner work) throws SQLException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Connection connection = (Connection) entityManager.unwrap(Connection.class);
            if (connection == null) {
                transaction.rollback();
                return false;
            } else {
                work.run(connection);
                transaction.commit();
                return true;
            }
        } catch (RuntimeException re) {
            transaction.rollback();
            return false;
        }
    }
}
