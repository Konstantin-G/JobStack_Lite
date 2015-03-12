package cz.garkusha.jobstack.view;

/**
 *  * Controller class show details of a positions.
 *
 * @author Konstantin Garkusha
 */
import cz.garkusha.jobstack.MainApp;
import cz.garkusha.jobstack.model.Position;
import cz.garkusha.jobstack.util.DateUtil;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class TableLayoutController {

    @FXML
    private TextField filterField;
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

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateUtil.DATE_PATTERN);

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
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());

        resultColumn.setCellValueFactory(cellData -> cellData.getValue().resultProperty());
        // Custom rendering of the table cell.
        resultColumn.setCellFactory(column -> {
            return new TableCell<Position, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Style all dates in March with a different color.
                        setText(item);
                        if ("ANSWER_NO".equals(item)) {
                            setTextFill(Color.BLACK);
                            setStyle("-fx-background-color: gray");
                        } else {
                            setTextFill(Color.BLACK);
                            setStyle("");
                        }
                    }
                }
            };
        });

        companyColumn.setCellValueFactory(cellData -> cellData.getValue().companyProperty());
        jobTitleColumn.setCellValueFactory(cellData -> cellData.getValue().jobTitleProperty());
        jobTitlePDFColumn.setCellValueFactory(cellData -> cellData.getValue().jobTitlePDFProperty());
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        webColumn.setCellValueFactory(cellData -> cellData.getValue().webProperty());
        personColumn.setCellValueFactory(cellData -> cellData.getValue().personProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        requestSentDateColumn.setCellValueFactory(cellData -> cellData.getValue().requestSentDateProperty());
        // Custom rendering of the requestSentDate cell.
        requestSentDateColumn.setCellFactory(column -> {
            return new TableCell<Position, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(dateTimeFormatter.format(item));
                    }
                }
            };
        });

        answerDateColumn.setCellValueFactory(cellData -> cellData.getValue().answerDateProperty());
        // Custom rendering of the answerDate cell.
        answerDateColumn.setCellFactory(column -> {
            return new TableCell<Position, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(dateTimeFormatter.format(item));
                    }
                }
            };
        });
        conversationColumn.setCellValueFactory(cellData -> cellData.getValue().conversationProperty());
    }

    /**
     * Is called by the setMainApp method to make ability to use String filter.
     */
    private void initializeFilter() {
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Position> filteredData = new FilteredList<>(mainApp.getPositions(), p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(position -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (position.getCompany().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches company name.
                } else if (position.getJobTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches job title.
                } else if (position.getLocation().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches location.
                } else if (position.getPerson().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches person name.
                } else if (position.getPhone().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches person phone.
                } else if (position.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches person email.
                } else if (DateUtil.toString(position.getRequestSentDate()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches request sent.
                }
                return false; // Does not match.
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Position> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(positionTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        positionTable.setItems(sortedData);
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
//        positionTable.setItems(mainApp.getPositions());
        // Add sorted list data to the table
        initializeFilter();
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
                    /*TODO don't work correctly in Linux(make application freezes), have to fix*/
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    System.out.println("Awt Desktop is not supported!");
                }

            } else {
                System.out.println("File \"" + absoluteReferenceToJobPDF + "\" is not exists!");
            }
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
     * details for a new position.
     */
    @FXML
    private void handleNewPerson() {
        Position tempPosition = new Position();
        boolean okClicked = mainApp.showPositionAddEditDialog(tempPosition);
        if (okClicked) {
            mainApp.getPositions().add(tempPosition);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected position.
     */
    @FXML
    private void handleEditPerson() {
        Position selectedPerson = positionTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            @SuppressWarnings("UnusedAssignment") boolean okClicked = mainApp.showPositionAddEditDialog(selectedPerson);
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Position Selected");
            alert.setContentText("Please select a position in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleSaveToDB() {
       mainApp.saveToDB();
       mainApp.setDataChanged(false);
    }
}
