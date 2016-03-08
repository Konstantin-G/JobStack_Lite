package cz.garkusha.jobstack_lite.controller;

import cz.garkusha.jobstack_lite.MainApp;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 *  * Controller class show details of a positions.
 *
 * @author Konstantin Garkusha
 */
public class RootLayoutController {

    private static final Logger LOG = LoggerFactory.getLogger(RootLayoutController.class);

    private MainApp mainApp;
    private TableLayoutController tableLayoutController;

    @FXML
    private void stop(){
        LOG.info("Controller was stopped");
        mainApp.stop();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp instance of main class
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }
    /**
     * Is called by the main application to give a reference to TableLayoutController.
     *
     * @param tableLayoutController instance of TableLayoutController class
     */
    public void setTableLayoutController(TableLayoutController tableLayoutController) {
        this.tableLayoutController = tableLayoutController;

    }

    /**
     * Called when the user clicks on Save  in menu.
     */
    @FXML
    private void handleSaveToDB() {
        tableLayoutController.handleSaveToDB();
    }
    /**
     * Called when the user clicks the new  in menu. Opens a dialog to edit
     * details for a new position.
     */
    @FXML
    private void handleNewPosition() {
        tableLayoutController.handleNewPosition();
    }
    /**
     * Called when the user clicks the edit  in menu. Opens a dialog to edit
     * details for the selected position.
     */
    @FXML
    private void handleEditPosition() {
        tableLayoutController.handleEditPosition();
    }

    /**
     * Called when the user clicks on the delete  in menu.
     */
    @FXML
    private void handleDeletePosition() {
        tableLayoutController.handleDeletePosition();
    }

    /**
     * Called when the user clicks on the Job Description in menu.
     */
    @FXML
    private void handleJobDescriptionHyperlink() {
        tableLayoutController.handleJobDescriptionHyperlink();
    }

    /**
     * Called when the user clicks on the Job on the web in menu.
     */
    @FXML
    private void handleJobOnWebHyperlink() {
        tableLayoutController.handleJobOnWebHyperlink();
    }

    /**
     * Opens a FileChooser to let the user select an datafile to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadPositionDataFromFile(file);
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePositionDataToFile(file);
        }
    }

    /**
     * Saves the file to the person file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
        File personFile = mainApp.getOpenedFile();
        if (personFile != null) {
            mainApp.savePositionDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Called when the user clicks on the About in menu.
     */
    @FXML
    private void handleAbout() {
        LOG.debug("About button was pressed");
        Dialogs.someError("Here will be some about program");
    }
}