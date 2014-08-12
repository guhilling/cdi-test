package de.hilling.junit.cdi.db;

import de.hilling.junit.cdi.lifecycle.TestEvent;
import de.hilling.junit.cdi.scope.EventType;
import de.hilling.junit.cdi.scope.TestSuiteScoped;
import org.junit.runner.Description;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

@TestSuiteScoped
public class ConnectionAnalyzer {
    private static final Logger LOG = Logger.getLogger(ConnectionAnalyzer.class.getCanonicalName());

    @Inject
    @Cleanup
    private Instance<Connection> connections;

    private List<ConnectionInfo> connectionInfos = new ArrayList<>();

    @PostConstruct
    public void init() {
        Iterator<Connection> iter = connections.iterator();
        while (iter.hasNext()) {
            final Connection connection = iter.next();
            final ConnectionInfo info = new ConnectionInfo();
            info.parse(connection);
            connectionInfos.add(info);
        }
    }

    public void beforeTestStarts(@Observes @TestEvent(EventType.STARTING) Description testDescription) {
        for (ConnectionInfo info : connectionInfos) {
            new ConnectionCleaner(info).cleanUp();
        }
    }

}
