package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The Product Class
 *
 */
public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> assParts = FXCollections.observableArrayList();

    /** Product object
     *
     * @param id Product ID
     * @param name Product Name
     * @param price Product price
     * @param stock Product Inventory Level
     * @param min Product min
     * @param max Product max
     * @param parts Product Associated Parts
     */
    public Product(int id, String name, double price, int stock, int min, int max, ObservableList<Part> parts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.assParts.addAll(parts);
    }

    /**
     *
     * @return the associated parts
     */
    public ObservableList<Part> getAssParts() {
        return assParts;
    }

    /** Clears then sets associated parts
     *
     * @param parts associated parts
     */
    public void addParts(ObservableList<Part> parts) {
        this.assParts.clear();
        this.assParts.addAll(parts);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }


}
