package de.hilling.junit.cdi.db;

public class H2ConstraintDisabler implements ConstraintDisabler {
    private final ConnectionInfo info;
    private final ConnectionUtil util;

    public H2ConstraintDisabler(ConnectionInfo info) {
        this.info = info;
        util = new ConnectionUtil();
        util.setConnection(info.getConnection());
    }

    @Override
    public void disableConstraints() {
        util.execute("SET REFERENTIAL_INTEGRITY FALSE");
    }

    @Override
    public void enableConstraints() {
        util.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }
}
