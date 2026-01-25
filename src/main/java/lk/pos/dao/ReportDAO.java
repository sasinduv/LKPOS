package lk.pos.dao;

import lk.pos.util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;

public class ReportDAO {

    //Daily sales
    public static double getTodayeSales() throws Exception {
        Connection con = DBConnection.getConnection();
        ResultSet rs = con.createStatement().executeQuery(
                "SELECT SUM(total) FROM sale WHERE DATE(date) = CURDATE()"
        );
        return rs.next() ? rs.getDouble(1) : 0;
    }

    //Monthly sale
    public static double getMonthlySales() throws Exception {
        Connection con = DBConnection.getConnection();
        ResultSet rs = con.createStatement().executeQuery(
          "SELECT SUM(total) FROM sale WHERE MONTH(date) = MONTH(CURDATE())"
        );
        return rs.next() ? rs.getDouble(1) : 0;
    }

    //Total Product sold

    public static int getTotalSoldQty() throws Exception {
        Connection con = DBConnection.getConnection();
        ResultSet rs = con.createStatement().executeQuery(
                "SELECT SUM(qty) FROM sale_item"
        );
        return rs.next() ? rs.getInt(1) : 0;
    }
}
