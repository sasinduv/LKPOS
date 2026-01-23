package lk.pos.controller;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import lk.pos.dao.ProductDAO;
import lk.pos.model.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductController {
    @FXML private TextField txtName;
    @FXML private TextField txtPrice;
    @FXML private TextField txtQty;

    @FXML private TableView<Product> tblProduct;
    @FXML private TableColumn<Product, Integer> colId;
    @FXML private TableColumn<Product, String> colName;
    @FXML private TableColumn<Product, Double> colPrice;
    @FXML private TableColumn<Product, Integer> colQty;

    public void initialize() {
        colId.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        colName.setCellValueFactory(data -> data.getValue().nameProperty());
        colPrice.setCellValueFactory(data -> data.getValue().priceProperty().asObject());
        colQty.setCellValueFactory(data -> data.getValue().qtyProperty().asObject());

        loadProducts();
    }

    @FXML
    public void addProduct() {
        try {
            String name = txtName.getText();
            double price = Double.parseDouble(txtPrice.getText());
            int qty = Integer.parseInt(txtQty.getText());

            ProductDAO.save(name, price, qty);

            clearFields();
            loadProducts(); // 🔄 refresh table

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadProducts() {
        try {
            ObservableList<Product> list =
                    FXCollections.observableArrayList(ProductDAO.getAll());
            tblProduct.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtName.clear();
        txtPrice.clear();
        txtQty.clear();
    }

    @FXML
    public void editProduct() {
        Product selected = tblProduct.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                ProductDAO.update(
                        selected.getId(),
                        txtName.getText(),
                        Double.parseDouble(txtPrice.getText()),
                        Integer.parseInt(txtQty.getText())
                );
                loadProducts();
                txtName.clear();
                txtPrice.clear();
                txtQty.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Please select a product to edit.");
        }
    }

    @FXML
    public void deleteProduct() {
        Product selected = tblProduct.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                ProductDAO.delete(selected.getId());
                loadProducts();
                txtName.clear();
                txtPrice.clear();
                txtQty.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Please select a product to delete.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
