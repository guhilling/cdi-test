package de.hilling.junit.cdi.db;

import de.hilling.junit.cdi.CdiUnitRunner;
import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.EventType;
import de.hilling.junit.cdi.scope.TestSuiteScoped;
import org.junit.runner.Description;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.sql.Connection;
import java.util.logging.Logger;

@TestSuiteScoped
public class DatabaseCleaner {
    private static final Logger LOG = Logger.getLogger(CdiUnitRunner.class
            .getCanonicalName());

    @Inject
    @Cleanup
    private Instance<Connection> connections;

    public void beforeTestStarts(@Observes @TestEvent(EventType.STARTING) Description testDescription) {
        for(Connection connection: connections) {
             LOG.fine("cleaning connection " + connection);
        }
    }
    public void beforeTestFinishes(@Observes @TestEvent(EventType.FINISHING) Description testDescription) {
    }
}
