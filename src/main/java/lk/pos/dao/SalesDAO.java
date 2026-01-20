package lk.pos.dao;

import lk.pos.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SalesDAO {

    public static void saveSale(double total, String method) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(
                "INSERT INTO sale(total,payment_method) VALUES (?,?)"
        );
        pst.setDouble(1, total);
        pst.setString(2, method);
        pst.executeUpdate();
    }
}

