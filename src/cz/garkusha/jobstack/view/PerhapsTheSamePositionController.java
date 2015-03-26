package cz.garkusha.jobstack.view;

import cz.garkusha.jobstack.model.Position;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;


/**
 * Controller class to compare new and other positions.
 * (if new position is probably the same)
 * @author Konstantin Garkusha
 */

public class PerhapsTheSamePositionController {

    @FXML
    private TextField newWebField;
    @FXML
    private TextField newResultField;
    @FXML
    private TextField newCompanyField;
    @FXML
    private TextField newJobTitleField;
    @FXML
    private TextField newLocationField;
    @FXML
    private TextField newPersonField;
    @FXML
    private TextField newRequestSentDateField;
    @FXML
    private TextField newAnswerDateField;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Set the position to be watched in the dialog.
     *
     * @param position for editing
     */
    public void setNewPosition(Position position) {
        newWebField.setText(position.getWeb());
        newResultField.setText("");
        newCompanyField.setText(position.getCompany());
        newJobTitleField.setText(position.getJobTitle());
        newLocationField.setText(position.getLocation());
        newPersonField.setText(position.getPerson());
        newRequestSentDateField.setText("");
        newAnswerDateField.setText("");
    }
}