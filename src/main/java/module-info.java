module lk.pos {
    requires javafx.controls;
    requires javafx.fxml;

    opens lk.pos.controller to javafx.fxml;
    exports lk.pos;
}