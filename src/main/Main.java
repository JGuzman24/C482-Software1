package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Main for program. Not the same as Main Menu.
 *
 */
public class Main extends Application{

    /** Loads Main Menu
     *
     * @param primaryStage sets Main Menu as primary stage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        primaryStage.setTitle("Inventory Management");
        primaryStage.setScene(new Scene(root, 1161, 525));
        primaryStage.show();
    }

    /** RunTime Error that occurred during development.
     * Add Product screen would crash after previously modifying a part then cancelling.
     * I had the all parts list married with the tables for both Main and Add Product.
     * Crash was fixed after separating allParts from the tables.
     */
    public static void runError(){
    }

    /** Future Enhancement. 
     * I would like to add the ability to select a part and see what Products have it as an associated part.
     *
     */
    public static void futureEnhancement(){
    }

    /** Main
     *
     * @param args
     */
    public static void  main(String[] args) {
        launch(args);
    }

}
