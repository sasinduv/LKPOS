package lk.pos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.pos.dao.ProductDAO;
import lk.pos.dao.SalesDAO;
import lk.pos.model.Product;
import lk.pos.model.SalesItem;
import lk.pos.util.BillPDFGenerator;

public class SalesController {

    @FXML private ComboBox<Product> cmbProduct;
    @FXML private ComboBox<String> cmbPayment;

    @FXML private TextField txtPrice;
    @FXML private TextField txtQty;
    @FXML private TextField txtCash;

    @FXML private Label lblTotal;
    @FXML private Label lblBalance;

    @FXML private TableView<SalesItem> tblBill;

    // ✅ REQUIRED VARIABLES
    private ObservableList<SalesItem> billList = FXCollections.observableArrayList();
    private double total = 0;

    @FXML
    public void initialize() throws Exception {

        cmbPayment.getItems().addAll("CASH", "CARD");
        cmbProduct.getItems().addAll(ProductDAO.getAllAvailable());

        // ✅ Show product name in ComboBox
        cmbProduct.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Product p, boolean empty) {
                super.updateItem(p, empty);
                setText(empty || p == null ? "" : p.getName());
            }
        });
        cmbProduct.setButtonCell(cmbProduct.getCellFactory().call(null));

        cmbProduct.setOnAction(e -> {
            Product p = cmbProduct.getValue();
            if (p != null) {
                txtPrice.setText(String.valueOf(p.getPrice()));
            }
        });

        setupTable();
    }

    private void setupTable() {

        TableColumn<SalesItem, String> colName = new TableColumn<>("Product");
        colName.setCellValueFactory(d -> d.getValue().nameProperty());

        TableColumn<SalesItem, Number> colQty = new TableColumn<>("Qty");
        colQty.setCellValueFactory(d -> d.getValue().qtyProperty());

        TableColumn<SalesItem, Number> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(d -> d.getValue().totalProperty());

        tblBill.getColumns().addAll(colName, colQty, colTotal);
        tblBill.setItems(billList);
    }

    @FXML
    void addItem() {

        Product p = cmbProduct.getValue();
        int qty = Integer.parseInt(txtQty.getText());

        if (p == null) {
            alert("Select a product!");
            return;
        }

        if (qty > p.getQty()) {
            alert("Not enough stock!");
            return;
        }

        double itemTotal = p.getPrice() * qty;
        total += itemTotal;

        billList.add(new SalesItem(
                p.getId(),
                p.getName(),
                qty,
                itemTotal
        ));

        lblTotal.setText("Total: " + total);
    }

    // ✅ FIXED METHOD NAME
    @FXML
    void completeSale() throws Exception {

        double cash = Double.parseDouble(txtCash.getText());
        double balance = cash - total;

        if (balance < 0) {
            alert("Insufficient cash!");
            return;
        }

        lblBalance.setText("Balance: " + balance);

        int saleId = SalesDAO.saveSale(total, cmbPayment.getValue());

        for (SalesItem item : billList) {
            SalesDAO.saveSaleItem(saleId, item);
            ProductDAO.reduceQty(item.getProductId(), item.getQty());
        }

        BillPDFGenerator.generate(
                saleId,
                billList,
                total,
                cash,
                balance
        );

        alert("Sale completed & bill downloaded!");
        reset();
    }

    private void reset() {
        billList.clear();
        total = 0;
        lblTotal.setText("Total: 0.00");
        lblBalance.setText("");
        txtCash.clear();
        txtQty.clear();
    }

    private void alert(String msg) {
        new Alert(Alert.AlertType.WARNING, msg).show();
    }
}
