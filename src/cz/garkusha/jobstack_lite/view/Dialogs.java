package cz.garkusha.jobstack_lite.view;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
/**
 * This class encapsulates all dialogs in the program
 *
 * @author Konstantin Garkusha
 */
public class Dialogs {
    /**
     * Called when we have any exception.
     */
    public static void exceptionDialog(Exception exception){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText(null);
        alert.setContentText(exception.getMessage());

    // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        // add css to dialog.
        alert.getDialogPane().getStylesheets().add(Dialogs.class.getResource("DarkTheme.css").toExternalForm());

        alert.showAndWait();
    }
    /**
     * Called when connection to internet in parsing time was lost.
     */
    public static void connectionError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Connection to internet was lost.\nTry again later, or fill fields manually");
        // add css to dialog.
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Dialogs.class.getResource("DarkTheme.css").toExternalForm());

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
        // add css to dialog.
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Dialogs.class.getResource("DarkTheme.css").toExternalForm());

        alert.showAndWait();
    }

    /**
     * Called when some error.
     */
    public static void someError (String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        // add css to dialog.
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Dialogs.class.getResource("DarkTheme.css").toExternalForm());

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
        // add css to dialog.
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Dialogs.class.getResource("DarkTheme.css").toExternalForm());

        alert.showAndWait();

    }
    /**
     * Called when the user clicks the edit button and position isn't selected
     */
    public static String saveToDBConfirmation(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText("Information wasn't saved to database!");
        alert.setContentText("Do you want to save information to database?");

        // add css to dialog.
        alert.getDialogPane().getStylesheets().add(Dialogs.class.getResource("DarkTheme.css").toExternalForm());

        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes){
           return "YES";
        } else if (result.get() == buttonTypeNo) {
            return "NO";
        }
        return "";
    }
}
