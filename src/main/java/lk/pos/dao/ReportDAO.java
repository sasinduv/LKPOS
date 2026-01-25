package lk.pos.dao;

import lk.pos.util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

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

    //Monthly sales grouped by month
    public static Map<String, Double> getMonthlySalesChart() throws Exception {
        Map<String, Double> map = new HashMap<>();

        Connection con = DBConnection.getConnection();
        ResultSet rs = con.createStatement().executeQuery(
                """
                SELECT MONTHNAME(date) AS month, SUM(total) AS total
                FROM sale
                GROUP BY MONTH(date)
                ORDER BY MONTH(date)
                """
        );

        while (rs.next()) {
            map.put(rs.getString("month"), rs.getDouble("total"));
        }
        return map;
    }


    //Payment method distribution
    public static Map<String, Integer> getPaymentChart() throws Exception {
        Map<String, Integer> map = new HashMap<>();

        Connection con = DBConnection.getConnection();
        ResultSet rs = con.createStatement().executeQuery(
                """
                SELECT payment_method, COUNT(*)
                FROM sale
                GROUP BY payment_method
                """
        );

        while (rs.next()) {
            map.put(rs.getString(1), rs.getInt(2));
        }
        return map;
    }
}
