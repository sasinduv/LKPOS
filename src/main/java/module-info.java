module lk.pos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens lk.pos.controller to javafx.fxml;
    exports lk.pos;
}