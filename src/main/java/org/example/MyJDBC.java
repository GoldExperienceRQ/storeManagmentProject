package org.example;

import java.sql.*;
public class MyJDBC {
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/store_schema", "root",
                "my_sql");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM products");
        while(rs.next()){
            System.out.println(rs.getString("product_name") + " " + rs.getString("amount"));
        }
    }
}
