package http;

import java.sql.*;


public class Sql {
    private static Connection con;
    public static void initialize(String name, String user, String password) {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + name, user,
                    password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection getConnection() {
        return con;
    }
}
