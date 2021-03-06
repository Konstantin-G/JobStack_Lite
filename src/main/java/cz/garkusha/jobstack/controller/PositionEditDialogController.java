package cz.garkusha.jobstack.controller;

/**
 * Controller class to create new or edit details of a position.
 *
 * @author Konstantin Garkusha
 */

import cz.garkusha.jobstack.MainApp;
import cz.garkusha.jobstack.model.Position;
import cz.garkusha.jobstack.model.PositionFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PositionEditDialogController {

    private static final Logger LOG = LoggerFactory.getLogger(PositionEditDialogController.class);

    @FXML
    private TextField idField;
    @FXML
    private ChoiceBox<String> resultChoiceBox;
    @FXML
    private TextField companyField;
    @FXML
    private TextField jobTitleField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField webField;
    @FXML
    private TextField personField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private DatePicker requestSentDateField;
    @FXML
    private DatePicker answerDateField;
    @FXML
    private TextArea conversationArea;
    @FXML
    private ChoiceBox<String> countryChoiceBox;

    private Stage dialogStage;
    private Position position;
    private boolean saveClicked = false;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        resultChoiceBox.setItems(FXCollections.observableArrayList("ANSWER_YES", "ANSWER_NO", "MY_ANSWER_NO", "INTERVIEW", "NO_ANSWER", "SMALL_SALARY"));
        countryChoiceBox.setItems(FXCollections.observableArrayList("Czech", "Russia", "Ukraine", "USA"));
        LOG.info("Controller initialisation");
    }

    /**
     * Is called by the TableControllerLayout to give a reference to mainApp.
     *
     * @param mainApp instance of main class
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage to sets the stage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the position to be edited in the dialog.
     *
     * @param position for editing
     */
    public void setPosition(Position position) {
        this.position = position;
        idField.setText(String.valueOf(position.getId()));
        resultChoiceBox.setValue(position.getResult());
        companyField.setText(position.getCompany());
        jobTitleField.setText(position.getJobTitle());
        locationField.setText(position.getLocation());
        webField.setText(position.getWeb());
        personField.setText(position.getPerson());
        phoneField.setText(position.getPhone());
        emailField.setText(position.getEmail());
        requestSentDateField.setValue(position.getRequestSentDate());
        requestSentDateField.setShowWeekNumbers(false);
        answerDateField.setValue(position.getAnswerDate());
        answerDateField.setShowWeekNumbers(false);
        conversationArea.setText(position.getConversation());
        countryChoiceBox.setValue(position.getCountry());
        LOG.debug("Position for editing was set");
    }

    /**
     * Sets the new position in the fill dialog.
     *
     * @param filledPosition new position with filled from web page
     */
    private void setFilledPosition(Position filledPosition) {
        idField.setText(String.valueOf(filledPosition.getId()));
        if (null != filledPosition.getResult())
            resultChoiceBox.setValue(filledPosition.getResult());
        if (null != filledPosition.getCompany())
            companyField.setText(filledPosition.getCompany());
        companyField.setPromptText("Can't find company name, need to fill this field manually");
        if (null != filledPosition.getJobTitle())
            jobTitleField.setText(filledPosition.getJobTitle());
        jobTitleField.setPromptText("Can't find job title, need to fill this field manually");
        if (null != filledPosition.getLocation())
            locationField.setText(filledPosition.getLocation());
        locationField.setPromptText("Can't find jobs location, need to fill this field manually");
        if (null != filledPosition.getWeb())
            webField.setText(filledPosition.getWeb());
        if (null != filledPosition.getPerson())
            personField.setText(filledPosition.getPerson());
        personField.setPromptText("Can't find contact person, You can to fill this field manually");
        if (null != filledPosition.getPhone())
            phoneField.setText(filledPosition.getPhone());
        phoneField.setPromptText("Can't find persons phone, You can to fill this field manually");
        if (null != filledPosition.getEmail())
            emailField.setText(filledPosition.getEmail());
        emailField.setPromptText("Can't find persons email, You can to fill this field manually");
        if (null != filledPosition.getRequestSentDate())
            requestSentDateField.setValue(filledPosition.getRequestSentDate());
        if (null != filledPosition.getAnswerDate())
            answerDateField.setValue(filledPosition.getAnswerDate());
        if (null != filledPosition.getConversation())
            conversationArea.setText(filledPosition.getConversation());
        conversationArea.setPromptText("Here you can type all conversation with contact person");
        LOG.debug("Filled position was set");
    }


    /**
     * Returns true if the user clicked Save, false otherwise.
     *
     * @return true or false
     */
    public boolean isSaveClicked() {
        return saveClicked;
    }

    /**
     * Called when the user clicks Save.
     */
    @FXML
    private void handleSave() {
        if (isInputValid()) {
            position.setId(Integer.parseInt(idField.getText()));
            if (null != resultChoiceBox.getValue()) {
                position.setResult(String.valueOf(resultChoiceBox.getValue()));
            } else {
                position.setResult("");
            }
            position.setCompany(companyField.getText());
            position.setJobTitle(jobTitleField.getText());
            position.setLocation(locationField.getText());
            position.setWeb(webField.getText());
            position.setPerson(personField.getText());
            position.setPhone(phoneField.getText());
            position.setEmail(emailField.getText());
            position.setRequestSentDate(requestSentDateField.getValue());
            position.setAnswerDate(answerDateField.getValue());
            position.setConversation(conversationArea.getText());
            position.setCountry(String.valueOf(countryChoiceBox.getValue()));

            // data was changed and when you click close you going to have dialog to save data to DB
            mainApp.setDataChanged(true);
            saveClicked = true;
            LOG.debug("Save button was pressed");
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        LOG.debug("Cancel button was pressed");
        dialogStage.close();
    }

    /**
     * Called when the user clicks fill.
     */
    @FXML
    private void handleFill() {
        LOG.debug("Fill button was pressed");
        int newId = mainApp.getPositionsMaxId();
        int id = Integer.parseInt(idField.getText());
        id = id != 0 ? id : newId + 1;
        String url = webField.getText();
        String country  = String.valueOf(countryChoiceBox.getValue());
        Position filledPosition = PositionFactory.getNewPosition(id, url, country);
        setFilledPosition(filledPosition);
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (companyField.getText() == null || companyField.getText().length() == 0) {
            errorMessage += "No valid company name!\n";
        }
        if (jobTitleField.getText() == null || jobTitleField.getText().length() == 0) {
            errorMessage += "No valid job title!\n";
        }
        if (locationField.getText() == null || locationField.getText().length() == 0) {
            errorMessage += "No valid location!\n";
        }

        /**TODO phone checking*/
        if (errorMessage.length() == 0) {
            LOG.debug("All input fields are valid");
            return true;
        } else {
            LOG.debug("Some fields are invalid: " + errorMessage);
            // Show the error message.
            Dialogs.invalidFieldsError(errorMessage);
            return false;
        }
    }
}
