package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import model.Inventory;
import model.Outsourced;
import model.InHouse;
import model.Part;


/** Controller for Modify Part screen.
 *
 */
public class ModifyPartController implements Initializable{

    public RadioButton inHouseMod;
    public RadioButton outsourcedMod;
    public Label categoryTypeMod;

    public boolean isOutsourcedMod = false;

    public TextField modPartIDText;
    public TextField modPartNameText;
    public TextField modPartInvText;
    public TextField modPartPriceText;
    public TextField modPartMaxText;
    public TextField modPartMinText;
    public TextField modPartMachineIDText;

    /** Initializer for Modify Part screen.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Part part = MainController.getModifyPart();
        getPart(part);
    }

    /** Selects In-House
     *
     * @param actionEvent In-House radio button
     */
    public void isInHouseMod(ActionEvent actionEvent) {
        categoryTypeMod.setText("MachineID");
        isOutsourcedMod = false;
        modPartMachineIDText.setText("");
    }

    /** Selects Outsourced
     *
     * @param actionEvent Outsourced radio button
     */
    public void isOutsourcedMod(ActionEvent actionEvent) {
        categoryTypeMod.setText("Company Name");
        isOutsourcedMod = true;
        modPartMachineIDText.setText("");
    }

    /** Returns to Main Screen. Does not save modification.
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

    /** Loads fields with information from selected part on Main screen.
     *
     * @param part Part selected for modification.
     */
    public void getPart(Part part){

        modPartIDText.setText(Integer.toString(part.getId()));
        modPartNameText.setText(part.getName());
        modPartInvText.setText(Integer.toString(part.getStock()));
        modPartPriceText.setText(Double.toString(part.getPrice()));
        modPartMinText.setText(Integer.toString(part.getMin()));
        modPartMaxText.setText(Integer.toString(part.getMax()));

        if (part instanceof Outsourced){
            modPartMachineIDText.setText(((Outsourced) part).getCompanyName());
            outsourcedMod.setSelected(true);
            categoryTypeMod.setText("Company Name");
            isOutsourcedMod = true;
        } else {
            modPartMachineIDText.setText(Integer.toString(((InHouse)part).getMachineID()));
            inHouseMod.setSelected(true);
            categoryTypeMod.setText("MachineID");
            isOutsourcedMod = false;
        }

    }

    /** Saves modification for part from all fields.
     *
     * @param actionEvent Save button.
     * @throws IOException
     */
    public void modifyPart(ActionEvent actionEvent) throws IOException{
        try{
            int partID = Integer.parseInt(modPartIDText.getText());
            Part part = Inventory.lookupPart(partID);
            Inventory.deletePart(part);

            int id = Integer.parseInt(modPartIDText.getText());
            String name = modPartNameText.getText();
            double price = Double.parseDouble(modPartPriceText.getText());
            int stock = Integer.parseInt(modPartInvText.getText());
            int min = Integer.parseInt(modPartMinText.getText());
            int max = Integer.parseInt(modPartMaxText.getText());
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
                if (isOutsourcedMod){
                    String companyName = modPartMachineIDText.getText();
                    Part partInHouse = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(partInHouse);
                } else {
                    int mID = Integer.parseInt(modPartMachineIDText.getText());
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
