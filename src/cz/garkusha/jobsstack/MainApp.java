package cz.garkusha.jobsstack;
/**
 *  Main application javaFX class.
 *
 * @author Konstantin Garkusha
 */
import java.io.IOException;

import cz.garkusha.jobsstack.model.Position;
import cz.garkusha.jobsstack.util.DBCommunication;
import cz.garkusha.jobsstack.view.PositionAddEditDialogController;
import cz.garkusha.jobsstack.view.TableLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

public class MainApp extends Application {
    private boolean isDataChanged;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private DBCommunication dbCommunication;

    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Position> positions = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        this.dbCommunication = new DBCommunication(positions);
        this.isDataChanged = false;
    }

     /**
     * Returns the data as an observable list of Persons.
     * @return
     */
    public ObservableList<Position> getPositions() {
        return positions;
    }

    public void setDataChanged(boolean isDataChanged) {
        this.isDataChanged = isDataChanged;
    }

    public void saveToDB(){
        dbCommunication.writePositionsToDB();
    }

    public int getPositionsMaxId(){
        int max = 0;
        for (Position p : positions){
            max = p.getId() > max ? p.getId() : max;
        }
        return max;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JobsStack");

        initRootLayout();
        showTableLayout();
        this.primaryStage.setOnCloseRequest(event -> {
            event.consume();
            if (isDataChanged){
                Action response = Dialogs.create()
                        .title("Warning!")
                        .masthead("Information wasn't saved to database!")
                        .message("Do you want to save information to database?")
                        .showConfirm();

                if (response == Dialog.ACTION_YES) {
                    saveToDB();
                    primaryStage.close();
                    System.exit(0);
                } else if (response == Dialog.ACTION_NO) {
                    primaryStage.close();
                    System.exit(0);
                }
            } else {
                primaryStage.close();
                System.exit(0);
            }
        });
    }

    /**
     * Initializes the root layout.
     */
    void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the table layout inside the root layout.
     */
    void showTableLayout() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TableLayout.fxml"));
            AnchorPane tableLayout = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(tableLayout);

            // Give the controller access to the main app.
            TableLayoutController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Opens a dialog to edit details for the specified position. If the user
     * clicks OK, the changes are saved into the provided position object and true
     * is returned.
     *
     * @param position the position object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showPositionAddEditDialog(Position position) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PositionAddEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Position");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the position into the controller.
            PositionAddEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPosition(position);
            controller.setMainApp(this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isSaveClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}