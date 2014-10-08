package de.hilling.junit.cdi.db;

public class ConstraintDisablerFactory {
    /**
     * Build constraint disabler for given ConnectionInfo.
     * @param info
     * @return ConstraintDisabler or null if not available.
     */
    public static ConstraintDisabler create(ConnectionInfo info) {
        switch (info.getType()) {
            case H2:
                return new H2ConstraintDisabler(info);
            case MYSQL:
                return new MysqlConstraintDisabler(info);
            default:
                return null;
        }
    }
}
