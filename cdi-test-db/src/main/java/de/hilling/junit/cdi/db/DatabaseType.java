package de.hilling.junit.cdi.db;

public enum DatabaseType {
    H2("H2"),
    MYSQL("MySQL");

    private final String typeString;

    DatabaseType(String typeString) {
        this.typeString = typeString;
    }

    public static DatabaseType byType(String typeString) {
        for (DatabaseType type : DatabaseType.values()) {
            if (type.typeString.equals(typeString)) {
                return type;
            }
        }
        throw new IllegalArgumentException("not found: " + typeString);
    }
}
