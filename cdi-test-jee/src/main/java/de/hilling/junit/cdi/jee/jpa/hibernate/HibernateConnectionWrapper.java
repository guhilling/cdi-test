package de.hilling.junit.cdi.jee.jpa.hibernate;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.internal.SessionImpl;

import de.hilling.junit.cdi.jee.jpa.ConnectionWrapper;
import de.hilling.junit.cdi.jee.jpa.DatabaseCleaner;

@RequestScoped
public class HibernateConnectionWrapper implements ConnectionWrapper {

    @Inject
    private Instance<DatabaseCleaner> cleaner;

    @Inject
    private EntityManager entityManager;

    @Override
    public boolean callDatabaseCleaner() {
        Object delegate = entityManager.getDelegate();
        if (delegate instanceof SessionImpl) {
            SessionImpl session = (SessionImpl) delegate;
            if(!cleaner.isUnsatisfied()) {
                session.doWork(connection -> cleaner.get().run(connection));
            }
            return true;
        } else {
            return false;
        }
    }
}
