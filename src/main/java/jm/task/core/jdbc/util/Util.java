package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public Connection getConnection() throws SQLException {
        String username = "root";
        String password = "postgres";
        String connectionUrl = "jdbc:mysql://localhost:3306/kata_1";
        return DriverManager.getConnection(connectionUrl, username, password);
    }
}
