package cz.garkusha.jobstack_lite.controller;

import cz.garkusha.jobstack_lite.model.Position;
import cz.garkusha.jobstack_lite.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller class to create new tab in TabPane.
 *
 * @author Konstantin Garkusha
 */

public class SamePositionTabController {

    private static final Logger LOG = LoggerFactory.getLogger(SamePositionTabController.class);

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
    private void initialize() {LOG.info("Controller initialisation");}

    /**
     * Set the samePosition to be watched in the dialog.
     */
    public void setOldPosition(Tab tab, Position filledPosition, Position samePosition) {
        final String STYLE = "-fx-background-color: #039ed3";
        tab.setText("id = " + samePosition.getId());

        oldWebField.setText(samePosition.getWeb());
        if (filledPosition.getWeb().toLowerCase().equals(samePosition.getWeb().toLowerCase())) {
            oldWebField.setStyle(STYLE);
        }

        oldResultField.setText(samePosition.getResult());

        oldCompanyField.setText(samePosition.getCompany());
        oldCompanyField.setStyle(STYLE);

        oldJobTitleField.setText(samePosition.getJobTitle());
        if (filledPosition.getJobTitle().toLowerCase().equals(samePosition.getJobTitle().toLowerCase())) {
            oldJobTitleField.setStyle(STYLE);
        }

        oldLocationField.setText(samePosition.getLocation());
        if (filledPosition.getLocation().toLowerCase().equals(samePosition.getLocation().toLowerCase())) {
            oldLocationField.setStyle(STYLE);
        }

        oldPersonField.setText(samePosition.getPerson());
        if (filledPosition.getPerson().toLowerCase().equals(samePosition.getPerson().toLowerCase())) {
            oldPersonField.setStyle(STYLE);
        }

        oldRequestSentDateField.setText(DateUtil.toString(samePosition.getRequestSentDate()));
        oldAnswerDateField.setText(DateUtil.toString(samePosition.getAnswerDate()));
        LOG.debug("Old position was set");
    }
}
