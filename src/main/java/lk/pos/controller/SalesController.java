package lk.pos.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lk.pos.dao.SalesDAO;

public class SalesController {

    @FXML private TextField txtTotal;
    @FXML private ComboBox<String> cmbPayment;

    @FXML
    public void initialize() {
        cmbPayment.getItems().addAll("CASH", "CARD");
    }

    @FXML
    void completeSale() throws Exception {
        SalesDAO.saveSale(
                Double.parseDouble(txtTotal.getText()),
                cmbPayment.getValue()
        );
    }
}

