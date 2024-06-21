package http.dao;

import http.Sql;
import http.entities.Good;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GoodDao implements GenericDao<Good> {

    Connection connection;

    public GoodDao() {
        connection = Sql.getConnection();
    }

    @Override
    public List<Good> readAll() {
        List<Good> goods = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM goods");

            while(rs.next()){
                goods.add(new Good(rs.getInt("product_id"), rs.getString("product_name"), rs.getDouble("price"), rs.getInt("quantity")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return goods;
    }

    @Override
    public void create(Good entity) throws SQLDataException {
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO goods (product_id, product_name, price, quantity) VALUES (" + entity.getId() + ",'" + entity.getName() + "', " + entity.getPrice() + ", " + entity.getQuantity() + ")");
        } catch (Exception e) {
            throw new SQLDataException("Invalid parameters");
        }
    }

    @Override
    public Good read(int id) {
        Good good = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM goods WHERE product_id = " + id);
            if(rs.next()){
             good = new Good(rs.getInt("product_id"), rs.getString("product_name"), rs.getDouble("price"), rs.getInt("quantity"));
            }
            else{

                System.out.println("No such good");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return good;
    }

    @Override
    public void update(Good entity) throws SQLDataException {
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE goods SET ";
            if(entity.getName() != null){
                query += "product_name = '" + entity.getName() + "', ";
            }
            if(entity.getPrice() != 0){
                query += "price = " + entity.getPrice() + ", ";
            }
            if(entity.getQuantity() != 0){
                query += "quantity = " + entity.getQuantity() + ", ";
            }
            query = query.substring(0, query.length() - 2);
            query += " WHERE product_id = " + entity.getId();
            statement.executeUpdate(query);
        } catch (Exception e) {
            throw new SQLDataException("Invalid parameters");
        }

    }

    @Override
    public void delete(int id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM goods WHERE product_id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
