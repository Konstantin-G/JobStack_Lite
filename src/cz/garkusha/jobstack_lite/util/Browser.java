package cz.garkusha.jobstack_lite.util;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Created by John Constantine on 5/3/2015.
 */
public class Browser  extends Region {

    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();

    public Browser(String urlOrHtml) {
        //apply the styles
        getStyleClass().add("browser");

        // load the web page
        if (urlOrHtml.length() < 500){
            // then we're thinking, that it's url
            webEngine.load(urlOrHtml);
        } else {
            // then we're thinking, that it's html content
            webEngine.loadContent(urlOrHtml);
        }

        //add the web view to the scene
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