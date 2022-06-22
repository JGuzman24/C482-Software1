package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Part;
import model.Inventory;
import model.Product;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for the Main Menu
 */
public class MainController implements Initializable {
    public TableView partTable;
    public TableColumn partID;
    public TableColumn partName;
    public TableColumn partInventory;
    public TableColumn partPrice;
    
    public TableView productTable;
    public TableColumn productID;
    public TableColumn productName;
    public TableColumn productInventory;
    public TableColumn productPrice;
    private static Part modifyPart;
    private static Product modifyProduct;

    public TextField searchPartText;
    public TextField searchProductText;
    public Label noPartFound;
    public Label noProductFound;


    /** Initializes The Main Menu.
     * Loads Part and Product Tables along with search bars and function buttons.
     *
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Inventory.insertTestData();

        partTable.setItems(Inventory.getAllParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(Inventory.getAllProducts());
        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    /** Takes user to Add Part Screen
     *
     * @param actionEvent Add Part Button
     * @throws Exception
     */
    public void toAddPart(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 575);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /** Takes user to Modify Part Screen
     *
     * @param actionEvent Modify Part Button
     * @throws IOException
     */
    public void toModifyPart(ActionEvent actionEvent) throws IOException {
        modifyPart = (Part) partTable.getSelectionModel().getSelectedItem();
        if(modifyPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Part Selected");
            alert.setContentText("Please Select a Part");
            alert.showAndWait();
            return;
        }

        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 575);
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.show();
    }


    /**
     *
     * @return Part that is to be modified.
     */
    public static Part getModifyPart(){
        return modifyPart;
    }

    /**
     *
     * @return Product that is to be modified.
     */
    public static Product getModifyProduct() { return modifyProduct;}

    /** Deletes Selected Part from Inventory
     *
     * @param actionEvent Delete Part Button
     */
    public void deletePart(ActionEvent actionEvent) {
        modifyPart = (Part) partTable.getSelectionModel().getSelectedItem();

        if(modifyPart == null){
            Alert noSelection = new Alert(Alert.AlertType.ERROR);
            noSelection.setTitle("No Part Selected");
            noSelection.setContentText("No Part is Selected");
            noSelection.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to delete selected Part?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Inventory.deletePart(modifyPart);
            }
        }

    }

    /** Takes user to Add Product screen.
     *
     * @param actionEvent Add Product button.
     * @throws IOException
     */
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1100, 631);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /** Takes user to Modify Product screen.
     *
     * @param actionEvent Modify Product button.
     * @throws IOException
     */
    public void toModifyProduct(ActionEvent actionEvent) throws IOException{
        modifyProduct = (Product) productTable.getSelectionModel().getSelectedItem();
        if(modifyProduct == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Product Selected");
            alert.setContentText("Please Select a Product");
            alert.showAndWait();
            return;
        }

        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1100, 631);
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.show();
    }

    /** Deletes Selected Product from Inventory
     *
     * @param actionEvent Delete Product button.
     */
    public void deleteProduct(ActionEvent actionEvent) {
        modifyProduct = (Product) productTable.getSelectionModel().getSelectedItem();

        if(modifyProduct == null){
            Alert noSelection = new Alert(Alert.AlertType.ERROR);
            noSelection.setTitle("No Product Selected");
            noSelection.setContentText("No Product is Selected");
            noSelection.showAndWait();
        }else if(!modifyProduct.getAssParts().isEmpty()){
            Alert noSelection = new Alert(Alert.AlertType.WARNING);
            noSelection.setTitle("Product has associated Parts");
            noSelection.setContentText("Associated Parts must be removed before deleting Product.\nPlease modify Product First.");
            noSelection.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to delete selected Product?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Inventory.deleteProduct(modifyProduct);
            }
        }

    }

    /** Searches part inventory for partial name match or part ID.
     * Is not case-sensitive.
     *
     * @param actionEvent Key press inside searchbar.
     */
    public void searchPart(KeyEvent actionEvent) {
        String partial = searchPartText.getText().toLowerCase();;
        ObservableList<Part> partsSearched = Inventory.lookupPartName(partial);
        if (partsSearched.isEmpty()){
            noPartFound.setOpacity(1);
        } else {
            noPartFound.setOpacity(0);
        }

        partTable.setItems(partsSearched);

    }

    /** Searches product inventory for partial name match or part ID.
     * Is not case-sensitive.
     *
     * @param actionEvent Key press inside searchbar.
     */
    public void searchProduct(KeyEvent actionEvent) {
        String partial = searchProductText.getText().toLowerCase();;
        ObservableList<Product> partsSearched = Inventory.lookupProductName(partial);
        if (partsSearched.isEmpty()){
            noProductFound.setOpacity(1);
        } else {
            noProductFound.setOpacity(0);
        }

        productTable.setItems(partsSearched);

    }

    /** Exits Program
     *
     * @param actionEvent Exit button
     */
    public void exitProgram(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to exit the program?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            System.exit(0);
        }
    }
}
