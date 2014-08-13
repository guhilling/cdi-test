package de.hilling.junit.cdi.db;

public class ConnectionCleaner {

    private final ConnectionInfo info;
    private final ConstraintDisabler disabler;

    public ConnectionCleaner(ConnectionInfo info) {
        this.info = info;
        disabler = ConstraintDisablerFactory.create(info);
    }

    public void cleanUp() {
        if(disabler!=null) {
            disabler.disableConstraints();
        }
    }
}
