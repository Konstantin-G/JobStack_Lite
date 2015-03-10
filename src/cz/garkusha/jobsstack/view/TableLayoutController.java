package cz.garkusha.jobsstack.view;

/**
 *  * Controller class show details of a positions.
 *
 * @author Konstantin Garkusha
 */
import cz.garkusha.jobsstack.MainApp;
import cz.garkusha.jobsstack.model.Position;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.controlsfx.dialog.Dialogs;

import java.awt.*;
import java.io.File;
import java.time.LocalDate;

public class TableLayoutController {
    @FXML
    private TableView<Position> positionTable;
    @FXML
    private TableColumn<Position, Number> idColumn;
    @FXML
    private TableColumn<Position, String> resultColumn;
    @FXML
    private TableColumn<Position, String> companyColumn;
    @FXML
    private TableColumn<Position, String> jobTitleColumn;
    @FXML
    private TableColumn<Position, String> jobTitlePDFColumn;
    @FXML
    private TableColumn<Position, String> locationColumn;
    @FXML
    private TableColumn<Position, String> webColumn;
    @FXML
    private TableColumn<Position, String> personColumn;
    @FXML
    private TableColumn<Position, String> phoneColumn;
    @FXML
    private TableColumn<Position, String> emailColumn;
    @FXML
    private TableColumn<Position, LocalDate> requestSentDateColumn;
    @FXML
    private TableColumn<Position, LocalDate> answerDateColumn;
    @FXML
    private TableColumn<Position, String> conversationColumn;



    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public TableLayoutController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the position table with the columns.
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        resultColumn.setCellValueFactory(cellData -> cellData.getValue().resultProperty());
        companyColumn.setCellValueFactory(cellData -> cellData.getValue().companyProperty());
        jobTitleColumn.setCellValueFactory(cellData -> cellData.getValue().jobTitleProperty());
        jobTitlePDFColumn.setCellValueFactory(cellData -> cellData.getValue().jobTitlePDFProperty());
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        webColumn.setCellValueFactory(cellData -> cellData.getValue().webProperty());
        personColumn.setCellValueFactory(cellData -> cellData.getValue().personProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        requestSentDateColumn.setCellValueFactory(cellData -> cellData.getValue().requestSentDateProperty());
        answerDateColumn.setCellValueFactory(cellData -> cellData.getValue().answerDateProperty());
        conversationColumn.setCellValueFactory(cellData -> cellData.getValue().conversationProperty());
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        positionTable.setItems(mainApp.getPositions());
    }

    /**
     * Called when the user clicks on the hyperlink button.
     */
    @FXML
    private void handleHyperlink() {
        try {
            String absoluteReferenceToJobPDF = System.getProperty("user.dir") + File.separator + positionTable.getFocusModel().getFocusedItem().getJobTitlePDF();
            File pdfFile = new File(absoluteReferenceToJobPDF);
            if (pdfFile.exists()) {

                if (Desktop.isDesktopSupported()) {
                    /*TODO don't work correctly in Linux(make application freezes), need to fix*/
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    System.out.println("Awt Desktop is not supported!");
                }

            } else {
                System.out.println("File is not exists!");
            }

            System.out.println("Done");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = positionTable.getSelectionModel().getSelectedIndex();
        positionTable.getItems().remove(selectedIndex);
        mainApp.setDataChanged(true);
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewPerson() {
        Position tempPosition = new Position();
        boolean okClicked = mainApp.showPositionAddEditDialog(tempPosition);
        if (okClicked) {
            mainApp.getPositions().add(tempPosition);
            MainApp.listSize++;
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditPerson() {
        Position selectedPerson = positionTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            @SuppressWarnings("UnusedAssignment") boolean okClicked = mainApp.showPositionAddEditDialog(selectedPerson);
        } else {
            // Nothing selected.
            Dialogs.create()
                    .title("No Selection")
                    .masthead("No Person Selected")
                    .message("Please select a person in the table.")
                    .showWarning();
        }
    }

    @FXML
    private void handleSaveToDB() {
       mainApp.saveToDB();
       mainApp.setDataChanged(false);
    }
}
