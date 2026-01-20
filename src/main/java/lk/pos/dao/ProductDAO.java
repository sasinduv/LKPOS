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
    }
