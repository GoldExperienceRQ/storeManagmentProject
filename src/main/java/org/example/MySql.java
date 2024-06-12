package org.example;

import java.sql.*;

public class MySql {
    private Connection con;

    public void initialize(String name, String user, String password) {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + name, "root",
                    "my_sql");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void read(Message message) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM products WHERE product_id = '" + message.getProduct_id() + "'");
            rs.next();
            System.out.println(rs.getString("product_id") + " " + rs.getString("product_name") + " " + rs.getString("amount") + " " + rs.getString("price"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void create(Message message) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO products (product_name, amount, price) VALUES ('" + message.getText() + "', '" +
                    message.getQuantity() + "', '" + message.getPrice() + "')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Message message) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE products SET amount = " + message.getQuantity() + ", price = " + message.getPrice() +
                    ", product_name = '" + message.getText() + "' WHERE product_id = " + message.getProduct_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void delete(Message message) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM products WHERE product_id = " + message.getProduct_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
