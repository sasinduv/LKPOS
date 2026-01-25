package lk.pos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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

    @FXML private VBox cardBox;
    @FXML private TextField txtCardNumber;
    @FXML private TextField txtExpiry;
    @FXML private TextField txtCVV;


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
//card payment
        cardBox.setVisible(false);

        cmbPayment.setOnAction(e -> {
            String method = cmbPayment.getValue();
            cardBox.setVisible("CARD".equals(method));
        });


        txtCash.textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                double cash = Double.parseDouble(newVal);
                double balance = cash - total;

                if (balance >= 0) {
                    lblBalance.setText("Balance: " + String.format("%.2f", balance));
                } else {
                    lblBalance.setText("Balance: 0.00");
                }
            } catch (NumberFormatException e) {
                lblBalance.setText("");
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

        if (p == null) {
            alert("Select a product!");
            return;
        }

        double qty;
        try {
            qty = Double.parseDouble(txtQty.getText()); // ✅ float qty
        } catch (NumberFormatException e) {
            alert("Enter valid quantity!");
            return;
        }

        if (qty <= 0) {
            alert("Quantity must be greater than 0");
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

        lblTotal.setText("Total: " + String.format("%.2f", total));

        // ✅ RESET FIELDS
        txtQty.clear();
        txtPrice.clear();
        cmbProduct.setValue(null);
    }

    @FXML
    void completeSale() throws Exception {

        if (cmbPayment.getValue() == null) {
            alert("Select payment method!");
            return;
        }

        if ("CARD".equals(cmbPayment.getValue())) {

            if (txtCardNumber.getText().isEmpty()
                    || txtExpiry.getText().isEmpty()
                    || txtCVV.getText().isEmpty()) {
                alert("Enter card details!");
                return;
            }

            // 💳 Here you can simulate payment success
            // (real payment gateway comes later)
        }

        double cash = 0;
        double balance = 0;

        if ("CASH".equals(cmbPayment.getValue())) {

            if (txtCash.getText().isEmpty()) {
                alert("Enter cash amount!");
                return;
            }

            try {
                cash = Double.parseDouble(txtCash.getText());
            } catch (NumberFormatException e) {
                alert("Invalid cash amount!");
                return;
            }

            balance = cash - total;

            if (balance < 0) {
                alert("Insufficient cash!");
                return;
            }

            lblBalance.setText("Balance: " + String.format("%.2f", balance));
        }


        int saleId = SalesDAO.saveSale(total, cmbPayment.getValue());

        for (SalesItem item : billList) {
            SalesDAO.saveSaleItem(saleId, item);
            ProductDAO.reduceQty(item.getProductId(), item.getQty());
        }

        BillPDFGenerator.generate(saleId, billList, total, cash, balance);

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
        txtPrice.clear();

        txtCardNumber.clear();
        txtExpiry.clear();
        txtCVV.clear();

        cmbPayment.setValue(null);
        cardBox.setVisible(false);
    }


    private void alert(String msg) {
        new Alert(Alert.AlertType.WARNING, msg).show();
    }
}
