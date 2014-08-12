package de.hilling.junit.cdi.db;

public class ConnectionCleaner {

    private final ConnectionInfo info;

    public ConnectionCleaner(ConnectionInfo info) {
        this.info = info;
    }

    public void cleanUp() {
    }
}
