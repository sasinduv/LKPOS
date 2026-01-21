package lk.pos.util;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void init() {

        String userTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INT AUTO_INCREMENT PRIMARY KEY,
                username VARCHAR(50) UNIQUE NOT NULL,
                password VARCHAR(255) NOT NULL,
                role VARCHAR(20) NOT NULL
            )
        """;

        String productTable = """
            CREATE TABLE IF NOT EXISTS product (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                price DECIMAL(10,2) NOT NULL,
                qty INT NOT NULL
            )
        """;

        String saleTable = """
            CREATE TABLE IF NOT EXISTS sale (
                id INT AUTO_INCREMENT PRIMARY KEY,
                total DECIMAL(10,2) NOT NULL,
                payment_method VARCHAR(20) NOT NULL,
                date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """;

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement()) {

            st.execute(userTable);
            st.execute(productTable);
            st.execute(saleTable);

            System.out.println("All tables checked/created successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
