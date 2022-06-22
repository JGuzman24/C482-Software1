package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import model.*;

/** Controller for Add Part Screen.
 *
 */
public class AddPartController implements Initializable {

    public RadioButton inHouseAdd;
    public RadioButton outsourcedAdd;
    public Label categoryTypeAdd;
    public TextField addPartIDText;
    public TextField addPartNameText;
    public TextField addPartInvText;
    public TextField addPartPriceText;
    public TextField addPartMinText;
    public TextField addPartMaxText;
    public TextField addPartMachineIDText;

    public boolean isOutsourcedAdd = false;

    /** Initializer for Add Part Screen.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPartIDText.setText(Integer.toString(Inventory.getNextPartID()));
    }

    /** Returns to Main Screen. Does not save.
     *
     * @param actionEvent Cancel button
     * @throws IOException
     */
    public void toStart(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to leave?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1161, 525);
            stage.setTitle("Start Screen");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** Selects In-House
     *
     * @param actionEvent In-House radio button
     */
    public void isInHouseAdd(ActionEvent actionEvent) {
        categoryTypeAdd.setText("MachineID");
        isOutsourcedAdd = false;
        addPartMachineIDText.setText("");
    }

    /** Selects Outsourced
     *
     * @param actionEvent Outsourced radio button
     */
    public void isOutsourcedAdd(ActionEvent actionEvent) {
        categoryTypeAdd.setText("Company Name");
        isOutsourcedAdd = true;
        addPartMachineIDText.setText("");
    }

    /** Saves entered information as a New Part in Inventory.
     *
     * @param actionEvent Save button
     * @throws IOException
     */
    public void addPartToList(ActionEvent actionEvent) throws IOException {
        try{
            int id = Integer.parseInt(addPartIDText.getText());
            String name = addPartNameText.getText();
            double price = Double.parseDouble(addPartPriceText.getText());
            int stock = Integer.parseInt(addPartInvText.getText());
            int min = Integer.parseInt(addPartMinText.getText());
            int max = Integer.parseInt(addPartMaxText.getText());
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
                if (isOutsourcedAdd){
                    String companyName = addPartMachineIDText.getText();
                    Part partInHouse = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(partInHouse);
                } else {
                    int mID = Integer.parseInt(addPartMachineIDText.getText());
                    Part partOutsourced = new InHouse(id, name, price, stock, min, max, mID);
                    Inventory.addPart(partOutsourced);
                }

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
}
