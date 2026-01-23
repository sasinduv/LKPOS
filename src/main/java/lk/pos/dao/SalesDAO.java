package lk.pos.dao;

import lk.pos.model.SalesItem;
import lk.pos.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SalesDAO {

    public static int saveSale(double total,String method) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(
                "INSERT INTO sale(total,payment_method) VALUES (?,?)",
                Statement.RETURN_GENERATED_KEYS
        );
        pst.setDouble(1, total);
        pst.setString(2, method);
        pst.executeUpdate();

        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }

        return -1;
    }

    public static void saveSaleItem(int saleId, SalesItem item) throws Exception {

        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(
                "INSERT INTO sale_item(sale_id, product_id, qty, price) VALUES (?,?,?,?)"
        );

        pst.setInt(1, saleId);
        pst.setInt(2, item.getProductId());
        pst.setInt(3, item.getQty());
        pst.setDouble(4, item.getTotal());

        pst.executeUpdate();
    }

}

