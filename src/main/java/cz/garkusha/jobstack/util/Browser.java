package cz.garkusha.jobstack.util;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Browser class
 *
 * @author Konstantin Garkusha
 */
public class Browser  extends Region {

    private static final Logger LOG = LoggerFactory.getLogger(Browser.class);

    private final WebView browser = new WebView();
    private final WebEngine webEngine = browser.getEngine();

    public Browser(String urlOrHtml) {
        LOG.info("Web browser initialisation");
        //apply the styles
        getStyleClass().add("browser");

        // load the web page
        if (urlOrHtml.length() < 500){
            // then we're thinking, that it's url
            LOG.debug("Load URL to Browser");
            webEngine.load(urlOrHtml);
        } else {
            LOG.debug("Load content to Browser");
            // then we're thinking, that it's html content
            webEngine.loadContent(urlOrHtml);
        }
        //add the web controller to the scene
        getChildren().add(browser);

    }
    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }

    @Override protected double computePrefWidth(double height) {
        return 750;
    }

    @Override protected double computePrefHeight(double width) {
        return 500;
    }
}