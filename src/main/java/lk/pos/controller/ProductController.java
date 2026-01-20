package lk.pos.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lk.pos.dao.ProductDAO;
import lk.pos.model.Product;

public class ProductController {
    @FXML private TextField txtName;
    @FXML private TextField txtPrice;
    @FXML private TextField txtQty;
    @FXML private TableView<Product> tblProduct;

    @FXML
    void addProduct() throws Exception {
        ProductDAO.save(
                txtName.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQty.getText())
        );
        loadProducts();
    }

    private void loadProducts() throws Exception {
        tblProduct.getItems().setAll(ProductDAO.getAll());
    }

    @FXML
    public void initialize() throws Exception {
        loadProducts();
    }



}
