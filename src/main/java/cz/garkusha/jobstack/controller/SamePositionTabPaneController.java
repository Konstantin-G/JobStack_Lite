package cz.garkusha.jobstack.controller;

import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Controller class to create new TabPane.
 *
 * @author Konstantin Garkusha
 */

public class SamePositionTabPaneController {

    private static final Logger LOG = LoggerFactory.getLogger(SamePositionTabPaneController.class);

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        LOG.info("Controller initialisation");
    }
}
