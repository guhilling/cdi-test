package de.hilling.junit.cdi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2ConnectionCreator {
    private static int index;

    private H2ConnectionCreator() {
    }

    public static Connection create() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:db-" + index++);
            ConnectionUtil util = new ConnectionUtil();
            util.setConnection(connection);
            util.execute("CREATE TABLE SAMPLE\n" +
                    "(\n" +
                    "   `ID`              varchar(63),\n" +
                    "   `DESCRIPTION`     varchar(255),\n" +
                    ")");
            util.execute("INSERT INTO SAMPLE VALUES('one', 'description-1')");
            util.execute("INSERT INTO SAMPLE VALUES('one', 'description-2')");
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
