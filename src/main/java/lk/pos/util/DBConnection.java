package lk.pos.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection connection;

    public static Connection getConnection() throws Exception {

        if (connection == null) {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/retail_pos?useSSL=false&serverTimezone=UTC",
                    "root",
                    ""
            );
        }
        return connection;
    }
}
