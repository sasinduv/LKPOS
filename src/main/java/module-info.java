module lk.pos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    opens lk.pos.controller to javafx.fxml;
    exports lk.pos;
}