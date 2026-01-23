package lk.pos.dao;

import lk.pos.model.Product;
import lk.pos.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public static void save(String name, double price, int qty) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(
                "INSERT INTO product(name,price,qty) VALUES (?,?,?)"
        );
        pst.setString(1, name);
        pst.setDouble(2, price);
        pst.setInt(3, qty);
        pst.executeUpdate();
    }

    public static void update(int id, String name, double price, int qty) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(
                "UPDATE product SET name=?, price=?, qty=? WHERE id=?"
        );
        pst.setString(1, name);
        pst.setDouble(2, price);
        pst.setInt(3, qty);
        pst.setInt(4, id);
        pst.executeUpdate();
    }

    public static void delete(int id) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(
                "DELETE FROM product WHERE id=?"
        );
        pst.setInt(1, id);
        pst.executeUpdate();
    }

    public static List<Product> getAll() throws Exception {
            List<Product> list = new ArrayList<>();
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM product");

            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("qty")
                ));
            }
            return list;
        }

        public static List<Product> getAllAvailable() throws Exception {
         List<Product> list = new ArrayList<>();
         Connection con = DBConnection.getConnection();
         ResultSet rs = con.createStatement().executeQuery("SELECT * FROM product WHERE qty > 0");

         while (rs.next()) {
             list.add(new Product(
                     rs.getInt("id"),
                     rs.getString("name"),
                     rs.getDouble("price"),
                     rs.getInt("qty")
             ));
         }
         return list;
        }public static void reduceQty(int productId, int soldQty) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement("UPDATE product SET qty = qty - ? WHERE id=?");
        pst.setInt(1, soldQty);
        pst.setInt(2, productId);
        pst.executeUpdate();
        }


    }
