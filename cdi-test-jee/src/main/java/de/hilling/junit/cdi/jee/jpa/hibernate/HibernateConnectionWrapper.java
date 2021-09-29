package de.hilling.junit.cdi.jee.jpa.hibernate;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.internal.SessionImpl;

import de.hilling.junit.cdi.jee.TestEntityResources;
import de.hilling.junit.cdi.jee.jpa.ConnectionWrapper;
import de.hilling.junit.cdi.jee.jpa.DatabaseCleaner;

/**
 * Wrapper for Hibernate to call {@link DatabaseCleaner}.
 */
@RequestScoped
public class HibernateConnectionWrapper implements ConnectionWrapper {

    @Inject
    private Instance<DatabaseCleaner> cleaner;

    @Override
    public void callDatabaseCleaner(EntityManager entityManager) {
        cleanEntityManager(entityManager);
    }

    private void cleanEntityManager(EntityManager entityManager) {
        Object delegate = entityManager.getDelegate();
        if (delegate instanceof SessionImpl) {
            SessionImpl session = (SessionImpl) delegate;
            if(!cleaner.isUnsatisfied()) {
                session.doWork(connection -> cleaner.get().run(connection));
            }
        }
    }
}
