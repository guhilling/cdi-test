package de.hilling.junit.cdi.db;

public class ConnectionCleaner {

    private final ConnectionInfo info;
    private final ConstraintDisabler disabler;
    private final ConnectionUtil util = new ConnectionUtil();

    public ConnectionCleaner(ConnectionInfo info) {
        this.info = info;
        disabler = ConstraintDisablerFactory.create(info);
        util.setConnection(info.getConnection());
    }

    public void cleanUp() {
        if (disabler == null) {
            cleanUpRecursing();
        } else {
            disabler.disableConstraints();
            try {
                cleanUpSimple();
            } finally {
                disabler.enableConstraints();
            }
        }
    }

    private void cleanUpRecursing() {
        throw new UnsupportedOperationException("not implemented");
    }

    private void cleanUpSimple() {
        for (String table : info.getTableNames()) {
             util.execute("delete from " + table);
        }
    }
}
