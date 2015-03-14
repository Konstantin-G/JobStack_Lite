package cz.garkusha.jobstack.view;

import javafx.scene.control.Alert;

/**
 * This class encapsulates all dialogs in the program
 *
 * @author Konstantin Garkusha
 */
public class Dialogs {
    /**
     * Called when connection to internet in parsing time was lost.
     */
    public static void connectionError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Connection to internet was lost.\nTry again later, or fill fields manually");

        alert.showAndWait();
    }
    /**
     * Called when at lease one positions field is invalid.
     */
    public static void invalidFieldsError (String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please correct invalid fields");
        alert.setContentText(errorMessage);

        alert.showAndWait();
    }
    /**
     * Called when the user clicks the edit button and position isn't selected
     */
    public static void noPositionSelectedError (){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Selection");
        alert.setHeaderText("No Position Selected");
        alert.setContentText("Please select a position in the table.");

        alert.showAndWait();
    }
}
