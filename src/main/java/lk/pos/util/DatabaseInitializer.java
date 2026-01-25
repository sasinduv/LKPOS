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
                qty DECIMAL(10,3) NOT NULL
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

        String saleBill = """
                CREATE TABLE IF NOT EXISTS sale_item (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    sale_id INT,
                    product_id INT,
                    qty INT,
                    price DECIMAL(10,2),
                    FOREIGN KEY (sale_id) REFERENCES sale(id),
                    FOREIGN KEY (product_id) REFERENCES product(id)
                )
                """;

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement()) {

            st.execute(userTable);
            st.execute(productTable);
            st.execute(saleTable);
            st.execute(saleBill);

            System.out.println("All tables checked/created successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
