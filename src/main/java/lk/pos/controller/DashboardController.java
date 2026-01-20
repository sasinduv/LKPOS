package lk.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lk.pos.model.User;

public class DashboardController {

    @FXML private Label lblWelcome;
    @FXML private Button btnProduct;
    @FXML private Button btnInventory;
    @FXML private Button btnSales;
    @FXML private Button btnReports;
    @FXML private Button btnLogout;

    public void setUser(User user) {
        lblWelcome.setText(
                "Welcome " + user.getUsername() + " (" + user.getRole() + ")"
        );

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

    @FXML
    private void openProduct(ActionEvent event) throws Exception {
        loadUI("/view/product.fxml", "Product Management");
    }

    @FXML
    private void openSales(ActionEvent event) throws Exception {
        loadUI("/view/sales.fxml", "Sales & Billing");
    }

    private void loadUI(String fxml, String title) throws Exception {
        Stage stage = new Stage();
        stage.setScene(new Scene(
                FXMLLoader.load(getClass().getResource(fxml))
        ));
        stage.setTitle(title);
        stage.show();
    }

    @FXML
    private void logout(ActionEvent event) {
        System.exit(0);
    }
}
