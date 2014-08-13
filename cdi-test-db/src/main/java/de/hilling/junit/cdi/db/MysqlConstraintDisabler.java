package de.hilling.junit.cdi.db;

public class MysqlConstraintDisabler implements ConstraintDisabler {
    private final ConnectionInfo info;
    private final ConnectionUtil util;

    public MysqlConstraintDisabler(ConnectionInfo info) {
        this.info = info;
        util = new ConnectionUtil();
        util.setConnection(info.getConnection());
    }

    @Override
    public void disableConstraints() {
        util.execute("SET foreign_key_checks = 0");
    }

    @Override
    public void enableConstraints() {
        util.execute("SET foreign_key_checks = 1");
    }
}
