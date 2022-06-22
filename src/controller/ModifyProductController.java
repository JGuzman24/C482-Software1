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

/** Controller for Modify Product screen.
 *
 */
public class ModifyProductController implements Initializable {

    public TextField addModPartSearch;
    public TextField modProductIDText;
    public TextField modProductName;
    public TextField modProductInv;
    public TextField modProductPrice;
    public TextField modProductMax;
    public TextField modProductMin;
    public TableView associatedModPartTable;
    public TableView addModPartTable;
    public TableColumn addModPartID;
    public TableColumn addModPartName;
    public TableColumn addModPartInventory;
    public TableColumn addModPartPrice;
    public TableColumn assModPartID;
    public TableColumn assModPartName;
    public TableColumn assModPartInventory;
    public TableColumn assModPartPrice;
    public ObservableList<Part> assModParts = FXCollections.observableArrayList();
    public Product product;
    public Label noPartFound;

    /** Initializer for Modify Product screen.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        product = MainController.getModifyProduct();
        getProduct(product);
        assModParts.addAll(product.getAssParts());

        addModPartTable.setItems(Inventory.getAllParts());
        addModPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        addModPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addModPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addModPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedModPartTable.setItems(assModParts);
        assModPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        assModPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assModPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assModPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** Loads parameter fields with selected product.
     *
     * @param product product selected from Main menu.
     */
    public void getProduct(Product product) {

        modProductIDText.setText(Integer.toString(product.getId()));
        modProductName.setText(product.getName());
        modProductInv.setText(Integer.toString(product.getStock()));
        modProductPrice.setText(Double.toString(product.getPrice()));
        modProductMin.setText(Integer.toString(product.getMin()));
        modProductMax.setText(Integer.toString(product.getMax()));
    }


    /** Returns to Main Menu without saving.
     *
     * @param actionEvent Save button.
     * @throws IOException
     */
    public void returnWithoutModify(ActionEvent actionEvent) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to leave?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1161, 525);
            stage.setTitle("Start Screen");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** Adds selected part as an associated part for Product
     *
     * @param actionEvent Add button.
     * @throws IOException
     */
    public void addModPartFromTable(ActionEvent actionEvent) throws IOException{
        Part partToAdd = (Part) addModPartTable.getSelectionModel().getSelectedItem();
        if(partToAdd == null){
            Alert noSelection = new Alert(Alert.AlertType.ERROR);
            noSelection.setTitle("No Part Selected");
            noSelection.setContentText("No Part is Selected");
            noSelection.showAndWait();
            return;
        }
        assModParts.add(partToAdd);

    }

    /** Search text field for part list. Is not case-sensitive.
     *
     * @param actionEvent searches on key press
     */
    public void searchModPart(KeyEvent actionEvent) {
        String partial = addModPartSearch.getText().toLowerCase();;
        ObservableList<Part> partsSearched = Inventory.lookupPartName(partial);
        if (partsSearched.isEmpty()){
            noPartFound.setOpacity(1);
        } else {
            noPartFound.setOpacity(0);
        }

        addModPartTable.setItems(partsSearched);
    }

    /** Saves all parameters and associated parts for selected Product in Inventory.
     *
     * @param actionEvent Save button.
     * @throws IOException
     */
    public void modifyProduct(ActionEvent actionEvent) throws IOException{
        try{
            int productID = Integer.parseInt(modProductIDText.getText());
            Product product = Inventory.lookupProduct(productID);

            String name = modProductName.getText();
            double price = Double.parseDouble(modProductPrice.getText());
            int stock = Integer.parseInt(modProductInv.getText());
            int min = Integer.parseInt(modProductMin.getText());
            int max = Integer.parseInt(modProductMax.getText());

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
                product.setId(productID);
                product.setName(name);
                product.setPrice(price);
                product.setStock(stock);
                product.setMin(min);
                product.setMax(max);
                product.addParts(assModParts);

                Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1161, 525);
                stage.setTitle("Start Screen");
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter valid values in text field.");
            alert.showAndWait();
        }
    }

    /** Removes selected part as an associated part from this Product.
     *
     * @param actionEvent Remove Associated Part button.
     * @throws IOException
     */
    public void removeModPart(ActionEvent actionEvent) throws IOException{
        Part partToRemove = (Part) associatedModPartTable.getSelectionModel().getSelectedItem();
        if(partToRemove == null){
            Alert noSelection = new Alert(Alert.AlertType.ERROR);
            noSelection.setTitle("No Part Selected");
            noSelection.setContentText("No Part is Selected");
            noSelection.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to remove selected Part?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                assModParts.remove(partToRemove);
            }
        }
    }
}
