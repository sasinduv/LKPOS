package lk.pos.model;

import javafx.beans.property.*;
public class SalesItem {
    private final IntegerProperty productId = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty qty = new SimpleIntegerProperty();
    private final DoubleProperty total = new SimpleDoubleProperty();

    public SalesItem(int productId, String name, int qty, double total) {
        this.productId.set(productId);
        this.name.set(name);
        this.qty.set(qty);
        this.total.set(total);
    }

    public int getProductId() { return productId.get(); }
    public int getQty() { return qty.get(); }
    public double getTotal() { return total.get(); }
    public String getName() { return name.get(); }

    public IntegerProperty qtyProperty() { return qty; }
    public DoubleProperty totalProperty() { return total; }
    public StringProperty nameProperty() { return name; }
}
