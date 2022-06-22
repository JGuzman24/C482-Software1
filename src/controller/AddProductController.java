package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for Add Product screen
 *
 */
public class AddProductController implements Initializable {

    public TextField addProductSearch;
    public TextField addProductIDText;
    public TextField addProductName;
    public TextField addProductInv;
    public TextField addProductPrice;
    public TextField addProductMax;
    public TextField addProductMin;
    public TableView associatedPartTable;
    public TableView addPartTable;
    public TableColumn addPartID;
    public TableColumn addPartName;
    public TableColumn addPartInventory;
    public TableColumn addPartPrice;
    public TableColumn assPartID;
    public TableColumn assPartName;
    public TableColumn assPartInventory;
    public TableColumn assPartPrice;
    private static ObservableList<Part> assParts = FXCollections.observableArrayList();
    public Label noPartFound;

    /** Initializer for Add Product Screen.
     * Displays full part list and associate part list.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPartTable.setItems(Inventory.getAllParts());
        addPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTable.setItems(assParts);
        assPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        assPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProductIDText.setText(Integer.toString(Inventory.getNextProductID()));
    }

    /** Returns to Main screen without saving.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void returnWithoutSave(ActionEvent actionEvent) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to leave?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1161, 525);
            stage.setTitle("Start Screen");
            stage.setScene(scene);
            stage.show();

            assParts.clear();
        }


    }

    /** Adds selected part to as an associated part for Product.
     *
     * @param actionEvent Add part button
     * @throws IOException
     */
    public void addPartFromTable(ActionEvent actionEvent) throws IOException{
        Part partToAdd = (Part) addPartTable.getSelectionModel().getSelectedItem();
        if(partToAdd == null){
            Alert noSelection = new Alert(Alert.AlertType.ERROR);
            noSelection.setTitle("No Part Selected");
            noSelection.setContentText("No Part is Selected");
            noSelection.showAndWait();
            return;
        }
        assParts.add(partToAdd);
    }

    /** Search text field for part list. Is not case-sensitive.
     *
     * @param actionEvent searches on key press
     */
    public void searchPart(KeyEvent actionEvent) {
        String partial = addProductSearch.getText().toLowerCase();
        ObservableList<Part> partsSearched = Inventory.lookupPartName(partial);
        if (partsSearched.isEmpty()){
            noPartFound.setOpacity(1);
        } else {
            noPartFound.setOpacity(0);
        }
        addPartTable.setItems(partsSearched);
    }

    /** Saves entered information and associated parts as a new Product in Inventory.
     *
     * @param actionEvent Save button
     * @throws IOException
     */
    public void saveProduct(ActionEvent actionEvent) throws IOException{

        try{
            int id = Integer.parseInt(addProductIDText.getText());
            String name = addProductName.getText();
            double price = Double.parseDouble(addProductPrice.getText());
            int stock = Integer.parseInt(addProductInv.getText());
            int min = Integer.parseInt(addProductMin.getText());
            int max = Integer.parseInt(addProductMax.getText());

            if (min > max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Logical Error");
                alert.setContentText("Min must be equal to or less than max.");
                alert.showAndWait();
            }else if(stock < min || stock > max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Logical Error");
                alert.setContentText("Inv must be between min and max");
                alert.showAndWait();
            }else {
                Product product = new Product(id, name, price, stock, min, max, assParts);
                Inventory.addProduct(product);
                System.out.println(product.getAssParts());

                Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1161, 525);
                stage.setTitle("Start Screen");
                stage.setScene(scene);
                stage.show();

                assParts.clear();
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter valid values in text field.");
            alert.showAndWait();
        }
    }

    /** Removes selected associated part from Product.
     *
     * @param actionEvent Remove Associated Part button.
     * @throws IOException
     */
    public void removePart(ActionEvent actionEvent) throws IOException{
        Part partToRemove = (Part) associatedPartTable.getSelectionModel().getSelectedItem();
        if(partToRemove == null){
            Alert noSelection = new Alert(Alert.AlertType.ERROR);
            noSelection.setTitle("No Part Selected");
            noSelection.setContentText("No Part is Selected");
            noSelection.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to remove selected Part?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                assParts.remove(partToRemove);
            }
        }
    }
}
