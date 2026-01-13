package lk.pos.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lk.pos.model.User;

public class DashboardController {

    @FXML private Label lblWelcome;
    @FXML private Button btnProduct;
    @FXML private Button btnInventory;
    @FXML private  Button btnSales;
    @FXML private  Button btnReports;

    public void setUser(User user) {
        lblWelcome.setText("Welcome" + user.getUsername() + " (" + user.getRole() + ")");

        switch (user.getRole()) {
            case "ADMIN":
                break;

            case "MANAGER":
                btnProduct.setDisable(true);
                break;

            case "CASHIER":
                btnProduct.setDisable(true);
                btnInventory.setDisable(true);
                btnReports.setDisable(true);
                break;
        }
    }
}
