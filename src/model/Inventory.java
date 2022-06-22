package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Inventory class.
 * Where all Part and Product data is stored.
 * Allows for cross-screen communication.
 *
 */
public class Inventory {

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static int nextPartID = 1;
    private static int nextProductID = 1;

    private static boolean firstTime = true;

    /**
     *
     * @return list of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     *
     * @return list of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     *
     * @param part part to add
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /**
     *
     * @param product product to add
     */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /**
     *
     * @param partID partID for lookup
     * @return part with matching ID
     */
    public static Part lookupPart(int partID) {
        for(Part p : allParts){
            if(p.getId() == partID){
                return p;
            }
        }
        return null;
    }

    /**
     *
     * @param productID productID for lookup
     * @return product with matching ID
     */
    public static Product lookupProduct(int productID) {
        for(Product p : allProducts){
            if(p.getId() == productID){
                return p;
            }
        }
        return null;
    }

    /**
     *
     * @return next ID available for new part
     */
    public static int getNextPartID() {
        for(int i = 1; i <= allParts.size()+1; i++) {
            if (lookupPart(i) == null) {
                nextPartID = i;
                return nextPartID;
            }
        }
        return nextPartID;
    }

    /**
     *
     * @return next ID available for new product
     */
    public static int getNextProductID() {
        for(int i = 1; i <= allProducts.size()+1; i++) {
            if (lookupProduct(i) == null) {
                nextProductID = i;
                return nextProductID;
            }
        }
        return nextProductID;
    }

    /** Searches all Parts for matching search string (name or ID)
     *
     * @param partialName search string
     * @return all parts that match name or ID
     */
    public static ObservableList<Part> lookupPartName(String partialName){
        ObservableList<Part> partName = FXCollections.observableArrayList();
        partialName.toLowerCase();

        for(Part p : allParts){
            if(p.getName().toLowerCase().contains(partialName) && !partName.contains(p)){
                partName.add(p);
            }
        }
        for(Part p : allParts){
            String idString = Integer.toString(p.getId());
            if(idString.contains(partialName) && !partName.contains(p)){
                partName.add(p);
            }
        }
        return partName;
    }

    /** Seaarches all Products for matching search string (name or ID)
     *
     * @param partialName search string
     * @return all products that match name or ID
     */
    public static ObservableList<Product> lookupProductName(String partialName){
        ObservableList<Product> productName = FXCollections.observableArrayList();
        partialName.toLowerCase();
        for(Product p : allProducts){
            if(p.getName().toLowerCase().contains(partialName) && !productName.contains(p)){
                productName.add(p);
            }
        }
        for(Product p : allProducts){
            String idString = Integer.toString(p.getId());
            if(idString.contains(partialName) && !productName.contains(p)){
                productName.add(p);
            }
        }
        return productName;
    }

    /** removes Part from inventory
     *
     * @param part part to remove
     */
    public static void deletePart(Part part) {
        allParts.remove(part);
    }

    /** removes Product from inventory
     *
     * @param product product to remove
     */
    public static void deleteProduct(Product product) {
        allProducts.remove(product);
    }

    /** Inserts test data
     *
     */
    public static void insertTestData(){
        if (!firstTime){
            return;
        }
        firstTime = false;

        System.out.println("Test data inserted");

        InHouse screw = new InHouse(1, "Screw", 0.67, 53, 0, 500, 2433);
        InHouse nut= new InHouse(2, "Nut", 0.50, 73, 0, 500, 888);
        InHouse cable = new InHouse(36, "Cable", 2, 21, 5, 100, 2216);

        Outsourced elbowJoint = new Outsourced(3, "Elbow Joint", 1.15, 13, 1, 99, "Bendeez");
        Outsourced spring = new Outsourced(6, "Spring", 1, 67, 10, 500, "Bounceez");
        Outsourced knob = new Outsourced(311, "Knob", 5.99, 4, 0, 50, "Bendeez");

        allParts.add(screw);
        allParts.add(nut);
        allParts.add(cable);
        allParts.add(elbowJoint);
        allParts.add(spring);
        allParts.add(knob);

        Product zip = new Product(1, "Zip", 99, 57, 10, 80, allParts);
        Product house = new Product(2, "House", 2000, 1, 1, 99, allParts);

        allProducts.add(zip);
        allProducts.add(house);

    }



}
