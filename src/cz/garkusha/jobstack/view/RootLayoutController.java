package cz.garkusha.jobstack.view;

import cz.garkusha.jobstack.MainApp;
import javafx.fxml.FXML;

/**
 *  * Controller class show details of a positions.
 *
 * @author Konstantin Garkusha
 */
public class RootLayoutController {
    private MainApp mainApp;
    private TableLayoutController tableLayoutController;

    @FXML
    private void stop(){
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
     * Called when the user clicks on the About in menu.
     */
    @FXML
    private void handleAbout() {
        Dialogs.someError("Here will be some about program");
    }
}