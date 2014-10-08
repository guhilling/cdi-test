package de.hilling.junit.cdi.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenericConstraintDisabler implements ConstraintDisabler {
    private final ConnectionInfo info;
    private final ConnectionUtil util;
    private final Collection<ForeignKey> foreignKeys;

    public GenericConstraintDisabler(ConnectionInfo info) {
        this.info = info;
        util = new ConnectionUtil();
        util.setConnection(info.getConnection());
        try {
            foreignKeys = findImportedKeys();
        } catch (SQLException e) {
            throw new RuntimeException("unable to analyze db", e);
        }
    }

    private Collection<ForeignKey> findImportedKeys() throws SQLException {
        List<ForeignKey> result = new ArrayList<>();
        for(String fk_table_name: info.getTableNames()) {
            final ResultSet resultSet =util.getConnection().getMetaData().getImportedKeys(null, null, fk_table_name);
            while(resultSet.next()) {
                final String fk_name = resultSet.getString("FK_NAME");
                final String pk_name = resultSet.getString("PK_NAME");
                final String pk_table_name = resultSet.getString("PKTABLE_NAME");
                final String pk_columnName = resultSet.getString("PKCOLUMN_NAME");
                final String fk_columnName = resultSet.getString("FKCOLUMN_NAME");
                result.add(new ForeignKey(fk_table_name, fk_name, pk_table_name, pk_name, pk_columnName, fk_columnName));
            }
        }
        return result;
    }

    private static class ForeignKey {
        final String fk_table;
        final String fk_name;
        final String pk_table;
        final String pk_name;
        final String pk_columnName;
        final String fk_columnName;

        private ForeignKey(String table, String fk_name, String pk_table, String pk_name, String pk_columnName, String fk_columnName) {
            this.fk_table = table;
            this.fk_name = fk_name;
            this.pk_table = pk_table;
            this.pk_name = pk_name;
            this.pk_columnName = pk_columnName;
            this.fk_columnName = fk_columnName;
        }
    }

    @Override
    public void disableConstraints() {
        for (ForeignKey foreignKey : foreignKeys) {
            util.execute("ALTER TABLE " + foreignKey.fk_table + " DROP CONSTRAINT " + foreignKey.fk_name);
        }
    }

    @Override
    public void enableConstraints() {
        for (ForeignKey foreignKey : foreignKeys) {
            util.execute("ALTER TABLE " + foreignKey.fk_table
                    + " ADD FOREIGN KEY (" + foreignKey.fk_columnName + ")"
                    + " REFERENCES " + foreignKey.pk_table + "(" + foreignKey.pk_columnName + ")");
        }
   }
}
