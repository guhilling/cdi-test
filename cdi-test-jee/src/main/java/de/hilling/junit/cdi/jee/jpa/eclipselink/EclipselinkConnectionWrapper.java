package de.hilling.junit.cdi.jee.jpa.eclipselink;

import de.hilling.junit.cdi.jee.jpa.ConnectionWrapper;
import de.hilling.junit.cdi.jee.jpa.Work;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.SQLException;

@RequestScoped
public class EclipselinkConnectionWrapper implements ConnectionWrapper {

    @Inject
    private EntityManager entityManager;

    @Override
    public void runWithConnection(final Work work) throws SQLException {
        Connection connection = (Connection) entityManager.unwrap(Connection.class);
        work.run(connection);
    }
}
