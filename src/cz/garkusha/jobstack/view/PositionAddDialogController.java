package cz.garkusha.jobstack.view;

/**
 * Controller class to create new or edit details of a position.
 *
 * @author Konstantin Garkusha
 */

import cz.garkusha.jobstack.MainApp;
import cz.garkusha.jobstack.model.Position;
import cz.garkusha.jobstack.model.PositionFactory;
import cz.garkusha.jobstack.util.PDFConverter;
import cz.garkusha.jobstack.util.Path;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PositionAddDialogController {

    @FXML
    private CheckBox updatePDFBox;
    @FXML
    private TextField idField;
    @FXML
    private ChoiceBox resultChoiceBox;
    @FXML
    private TextField companyField;
    @FXML
    private TextField jobTitleField;
    @FXML
    private TextField pathToThePDFField;
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
        // add listener to automation make changes in path to pdf file, when company name was changed
        companyField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                position.setCompany(newValue);
                position.setJobTitlePDF(Path.getRelativeJobDescriptionPath() + PositionFactory.getPDFFileName(position.getCompany(), position.getJobTitle()));
                pathToThePDFField.setText(position.getJobTitlePDF());
            }
        });
        // add listener to automation make changes in path to pdf file, when job title was changed
        jobTitleField.setText(position.getJobTitle());
        jobTitleField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                position.setJobTitle(newValue);
                position.setJobTitlePDF(Path.getRelativeJobDescriptionPath() + PositionFactory.getPDFFileName(position.getCompany(), position.getJobTitle()));
                pathToThePDFField.setText(position.getJobTitlePDF());
            }
        });
        pathToThePDFField.setText(position.getJobTitlePDF());
        locationField.setText(position.getLocation());
        webField.setText(position.getWeb());
        webField.setPromptText("Correctly working only with: \"m.jobs.cz\", \"jobdnes.cz\", \"prace.cz\".");
        personField.setText(position.getPerson());
        phoneField.setText(position.getPhone());
        emailField.setText(position.getEmail());
        requestSentDateField.setValue(position.getRequestSentDate());
        requestSentDateField.setShowWeekNumbers(false);
        requestSentDateField.setPromptText("dd.mm.yyyy");
        answerDateField.setValue(position.getAnswerDate());
        answerDateField.setShowWeekNumbers(false);
        answerDateField.setPromptText("dd.mm.yyyy");
        conversationArea.setText(position.getConversation());
    }

    /**
     * Sets the new position in the fill dialog.
     *
     * @param filledPosition new position with filled from web page
     */
    void setFilledPosition(Position filledPosition) {
        idField.setText(String.valueOf(filledPosition.getId()));
        resultChoiceBox.setValue(filledPosition.getResult());
        companyField.setText(filledPosition.getCompany());
        companyField.setPromptText("Can't find company name, need to fill this field manually");
        jobTitleField.setText(filledPosition.getJobTitle());
        jobTitleField.setPromptText("Can't find job title, need to fill this field manually");
        pathToThePDFField.setText(filledPosition.getJobTitlePDF());
        locationField.setText(filledPosition.getLocation());
        locationField.setPromptText("Can't find jobs location, need to fill this field manually");
        webField.setText(filledPosition.getWeb());
        personField.setText(filledPosition.getPerson());
        personField.setPromptText("Can't find contact person, You can to fill this field manually");
        phoneField.setText(filledPosition.getPhone());
        phoneField.setPromptText("Can't find persons phone, You can to fill this field manually");
        emailField.setText(filledPosition.getEmail());
        emailField.setPromptText("Can't find persons email, You can to fill this field manually");
        requestSentDateField.setValue(filledPosition.getRequestSentDate());
        requestSentDateField.setPromptText("dd.mm.yyyy");
        answerDateField.setValue(filledPosition.getAnswerDate());
        answerDateField.setPromptText("dd.mm.yyyy");
        conversationArea.setText(filledPosition.getConversation());
        conversationArea.setPromptText("Here you can type all conversation with contact person");
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
            position.setJobTitlePDF(pathToThePDFField.getText());
            // save web page to pdf from webField to pathToThePDFField
            new PDFConverter(webField.getText(), pathToThePDFField.getText());
            position.setLocation(locationField.getText());
            position.setWeb(webField.getText());
            position.setPerson(personField.getText());
            position.setPhone(phoneField.getText());
            position.setEmail(emailField.getText());
            position.setRequestSentDate(requestSentDateField.getValue());
            position.setAnswerDate(answerDateField.getValue());
            position.setConversation(conversationArea.getText());

            // data was changed and when you click close you going to have dialog to save data to DB
            mainApp.setDataChanged(true);
            saveClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Called when the user clicks fill.
     */
    @FXML
    private void handleFill() {
        int newId = mainApp.getPositionsMaxId();
        int id = Integer.parseInt(idField.getText());
        id = id != 0 ? id : newId + 1;
        String url = webField.getText();
        Position filledPosition = PositionFactory.getNewPosition(id, url);
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
            errorMessage += "No valid jo title!\n";
        }
        /** TODO  file checking*/
        if (pathToThePDFField.getText() == null || pathToThePDFField.getText().length() == 0) {
            errorMessage += "No valid path to pdf!\n";
        }
        if (locationField.getText() == null || locationField.getText().length() == 0) {
            errorMessage += "No valid location!\n";
        }
        if (webField.getText() == null || webField.getText().length() == 0) {
            errorMessage += "No valid url!\n";
        }
        /**TODO phone checking*/
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Dialogs.invalidFieldsError(errorMessage);
            return false;
        }
    }
}
