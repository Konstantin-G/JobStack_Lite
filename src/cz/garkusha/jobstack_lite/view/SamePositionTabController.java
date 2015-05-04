package cz.garkusha.jobstack_lite.view;

import cz.garkusha.jobstack_lite.model.Position;
import cz.garkusha.jobstack_lite.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

/**
 * Controller class to create new tab in TabPane.
 *
 * @author Konstantin Garkusha
 */

public class SamePositionTabController {

    @FXML
    private TextField oldWebField;
    @FXML
    private TextField oldResultField;
    @FXML
    private TextField oldCompanyField;
    @FXML
    private TextField oldJobTitleField;
    @FXML
    private TextField oldLocationField;
    @FXML
    private TextField oldPersonField;
    @FXML
    private TextField oldRequestSentDateField;
    @FXML
    private TextField oldAnswerDateField;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {}

    /**
     * Set the position to be watched in the dialog.
     */
    public void setOldPosition(Tab tab, Position position) {
        tab.setText("id = " + position.getId());
        oldWebField.setText(position.getWeb());
        oldResultField.setText(position.getResult());
        oldCompanyField.setText(position.getCompany());
        oldJobTitleField.setText(position.getJobTitle());
        oldLocationField.setText(position.getLocation());
        oldPersonField.setText(position.getPerson());
        oldRequestSentDateField.setText(DateUtil.toString(position.getRequestSentDate()));
        oldAnswerDateField.setText(DateUtil.toString(position.getAnswerDate()));
    }
}
