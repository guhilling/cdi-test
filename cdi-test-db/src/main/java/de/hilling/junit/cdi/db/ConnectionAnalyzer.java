package de.hilling.junit.cdi.db;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConnectionAnalyzer {
    @Inject
    @Cleanup
    private Instance<Connection> connections;

    private Map<Connection, ConnectionInfo> connectionMap = new HashMap<>();

    @PostConstruct
    public void init() {
        Iterator<Connection> iter = connections.iterator();
        while (iter.hasNext()) {
            final Connection connection = iter.next();
            final ConnectionInfo info = new ConnectionInfo();
            info.parse(connection);
            connectionMap.put(connection, info);
        }
    }

    public ConnectionInfo getInfoForConnection(Connection connection) {
        return connectionMap.get(connection);
    }
}
