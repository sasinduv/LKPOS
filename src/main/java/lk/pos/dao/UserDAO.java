package lk.pos.dao;

import lk.pos.model.User;
import lk.pos.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public static User login(String username, String password) throws Exception {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT role FROM users WHERE username=? AND password=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, username);
        pst.setString(2, password);

        ResultSet rs= pst.executeQuery();
        if (rs.next()) {
            return new User(username, rs.getString("role"));
        }
        return null;
    }
}
