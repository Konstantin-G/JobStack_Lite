package cz.garkusha.jobstack.view;

/**
 *  * Controller class show details of a positions.
 *
 * @author Konstantin Garkusha
 */
import cz.garkusha.jobstack.MainApp;
import cz.garkusha.jobstack.model.Position;
import cz.garkusha.jobstack.util.DateUtil;
import cz.garkusha.jobstack.util.DeletePositionsPDF;
import cz.garkusha.jobstack.util.Path;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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
    @FXML
    private TableColumn<Position, String> countryColumn;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateUtil.DATE_PATTERN);
    SortedList<Position> sortedData;
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
        // Custom rendering of the result cell.
        resultColumn.setCellFactory(column -> new TableCell<Position, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    // Style all dates in March with a different color.
                    //"ANSWER_YES", "ANSWER_NO", "MY_ANSWER_NO", "INTERVIEW", "NO_ANSWER", "SMALL_SALARY"
                    setText(item);
                    if ("ANSWER_YES".equals(item)) {
                        setTextFill(Color.LIGHTCORAL);
                        setStyle("-fx-background-color: #580000");
                    } else if ("ANSWER_NO".equals(item)) {
                        setTextFill(Color.LIGHTGRAY);
                        setStyle("-fx-background-color: #202020");
                    } else if ("MY_ANSWER_NO".equals(item)) {
                        setTextFill(Color.DARKRED);
                        setStyle("-fx-background-color: #FF3300");
                    } else if ("INTERVIEW".equals(item)) {
                        setTextFill(Color.DARKMAGENTA);
                        setStyle("-fx-background-color: #FF9900");
                    } else if ("NO_ANSWER".equals(item)) {
                        setTextFill(Color.GRAY);
                        setStyle("-fx-background-color: #58584F");
                    } else if ("SMALL_SALARY".equals(item)) {
                        setTextFill(Color.DARKCYAN);
                        setStyle("-fx-background-color: #858594");
                    } else {
                        setTextFill(Color.BLACK);
                        setStyle("");
                    }
                }
            }
        });

        companyColumn.setCellValueFactory(cellData -> cellData.getValue().companyProperty());
        jobTitleColumn.setCellValueFactory(cellData -> cellData.getValue().jobTitleProperty());
        jobTitlePDFColumn.setCellValueFactory(cellData -> cellData.getValue().htmlProperty());
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        webColumn.setCellValueFactory(cellData -> cellData.getValue().webProperty());
        personColumn.setCellValueFactory(cellData -> cellData.getValue().personProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        requestSentDateColumn.setCellValueFactory(cellData -> cellData.getValue().requestSentDateProperty());
        // Custom rendering of the requestSentDate cell.
        requestSentDateColumn.setCellFactory(column -> new TableCell<Position, LocalDate>() {
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
        });

        answerDateColumn.setCellValueFactory(cellData -> cellData.getValue().answerDateProperty());
        // Custom rendering of the answerDate cell.
        answerDateColumn.setCellFactory(column -> new TableCell<Position, LocalDate>() {
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
        });
        conversationColumn.setCellValueFactory(cellData -> cellData.getValue().conversationProperty());
        countryColumn.setCellValueFactory(cellData -> cellData.getValue().countryProperty());
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
                // If filter text is empty, display all positions.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare some fields of every position with filter text.
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
        sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(positionTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        positionTable.setItems(sortedData);
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp instance of main class
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
//        positionTable.setItems(mainApp.getPositions());
        // Add sorted list data to the table
        initializeFilter();
    }

    /**
     * Called when the user clicks on the Job Description hyperlink.
     */
    @FXML
    void handleJobDescriptionHyperlink() {
        Position selectedPerson = positionTable.getSelectionModel().getSelectedItem();
        if (null != selectedPerson){
           String html = selectedPerson.getHtml();
            mainApp.showInternetBrowser(html);
        }else{
            // Nothing selected.
            Dialogs.noPositionSelectedError();
        }
    }

    /**
     * Called when the user clicks on the Job on the web hyperlink.
     */
    @FXML
    void handleJobOnWebHyperlink() {
        Position selectedPerson = positionTable.getSelectionModel().getSelectedItem();
        if (null != selectedPerson) {
            //Set your page url in this string. For eg, I m using URL for Google Search engine
            String url = selectedPerson.getWeb();
            mainApp.showInternetBrowser(url);
        }else{
            // Nothing selected.
            Dialogs.noPositionSelectedError();
        }
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    void handleDeletePosition() {
        // The index of the sorted and filtered list.
        int visibleIndex = positionTable.getSelectionModel().getSelectedIndex();

        if (-1 != visibleIndex) {
            // Source index of master data.
            int sourceIndex = sortedData.getSourceIndexFor(mainApp.getPositions(), visibleIndex);

            // Remove.
            mainApp.getPositions().remove(sourceIndex);
            mainApp.setDataChanged(true);
        }else {
            // Nothing selected.
            Dialogs.noPositionSelectedError();
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new position.
     */
    @FXML
    void handleNewPosition() {
        Position tempPosition = new Position();
        boolean okClicked = mainApp.showPositionAddDialog(tempPosition);
        if (okClicked) {
            mainApp.getPositions().add(tempPosition);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected position.
     */
    @FXML
    void handleEditPosition() {
        Position selectedPerson = positionTable.getSelectionModel().getSelectedItem();
        if (null != selectedPerson) {
            @SuppressWarnings("UnusedAssignment") boolean okClicked = mainApp.showPositionEditDialog(selectedPerson);
        } else {
            // Nothing selected.
           Dialogs.noPositionSelectedError();
        }
    }
    /**
     * Called when the user clicks on Save to database button.
     */
    @FXML
    public void handleSaveToDB() {
        mainApp.saveToDB();
        mainApp.setDataChanged(false);
    }

    /**
     * Called when the user double mouse clicks on the table. Opens a dialog to edit
     * details for the selected position.
     */
    @FXML
    private void handleMouseClicked(MouseEvent event) {
        // handle the event here
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                handleEditPosition();
            }
//            if (event.getClickCount() == 1) {
//                System.out.println("Single clicked A_button");
//            }
        }
    }
}
