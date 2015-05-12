package de.hilling.junit.cdi.jee.jpa.hibernate;

import de.hilling.junit.cdi.jee.jpa.ConnectionWrapper;
import de.hilling.junit.cdi.jee.jpa.Work;
import org.hibernate.internal.SessionImpl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.SQLException;

@RequestScoped
public class HibernateConnectionWrapper implements ConnectionWrapper {

    @Inject
    private EntityManager entityManager;

    public boolean runWithConnection(final Work work) {
        Object delegate = entityManager.getDelegate();
        if (delegate instanceof SessionImpl) {
            SessionImpl session = (SessionImpl) delegate;
            session.doWork(new org.hibernate.jdbc.Work() {
                @Override
                public void execute(Connection connection) throws SQLException {
                    work.run(connection);
                }
            });
            return true;
        } else {
            return false;
        }
    }
}
