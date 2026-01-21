package lk.pos.model;

import javafx.beans.property.*;

public class Product {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();
    private final IntegerProperty qty = new SimpleIntegerProperty();

    public Product(int id, String name, double price, int qty) {
        this.id.set(id);
        this.name.set(name);
        this.price.set(price);
        this.qty.set(qty);
    }

    // Property getters (for TableView binding)
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public IntegerProperty qtyProperty() {
        return qty;
    }

    // Optional normal getters (useful later)
    public int getId() { return id.get(); }
    public String getName() { return name.get(); }
    public double getPrice() { return price.get(); }
    public int getQty() { return qty.get(); }
}
