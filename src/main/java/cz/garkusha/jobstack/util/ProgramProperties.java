package cz.garkusha.jobstack.util;

import java.awt.*;
import java.io.*;
import java.util.Properties;

/**
 *  Class to store all properties.
 *
 * @author Konstantin Garkusha
 */
public class ProgramProperties {

    private static ProgramProperties instance = null;

    private String lastCountry;
    private boolean isMainMaximized;
    private Rectangle rootLayout;
    private Rectangle addLayout;
    private Rectangle editLayout;
    private Rectangle browserLayout;
    private String lastPathToDB;

    // constructor
    private ProgramProperties (){
        loadProperties();
    }

    public static ProgramProperties getInstance(){
        if (instance == null){
            instance = new ProgramProperties();
        }
        return instance;
    }

    public String getLastCountry() {
        return lastCountry;
    }

    public void setLastCountry(String lastCountry) {
        this.lastCountry = lastCountry;
    }

    public boolean isMainMaximized() {
        return isMainMaximized;
    }

    public void setMainMaximized(boolean mainMaximized) {
        this.isMainMaximized = mainMaximized;
    }

    public Rectangle getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(Rectangle rootLayout) {
        this.rootLayout = rootLayout;
    }

    public Rectangle getAddLayout() {
        return addLayout;
    }

    public void setAddLayout(Rectangle addLayout) {
        this.addLayout = addLayout;
    }

    public Rectangle getEditLayout() {
        return editLayout;
    }

    public void setEditLayout(Rectangle editLayout) {
        this.editLayout = editLayout;
    }

    public Rectangle getBrowserLayout() {
        return browserLayout;
    }

    public void setBrowserLayout(Rectangle browserLayout) {
        this.browserLayout = browserLayout;
    }

    public String getlastPathToDB() {
        return lastPathToDB;
    }

    public void setlastPathToDB(String lastPathToDB) {
        this.lastPathToDB = lastPathToDB;
    }

    private void loadProperties(){
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Can't find config file, I'll create one");
            this.lastCountry = "Czech";
            this.rootLayout = new Rectangle(100, 100, 1400, 400);
        }

        if(properties.containsKey("lastCountry")) {
            // web page lastCountry
            this.lastCountry = properties.getProperty("lastCountry") != null ? properties.getProperty("lastCountry") : "Czech";
        } else this.lastCountry = "Czech";

        if (properties.containsKey("isMainMaximized")) {
            // fullscreen main window
            this.isMainMaximized = "1".equals(properties.getProperty("isMainMaximized"));
        } else this.isMainMaximized = false;

        if (properties.containsKey("primaryStageX") && properties.containsKey("primaryStageY") &&
                properties.containsKey("primaryStageWidth") && properties.containsKey("primaryStageHeight")){
            // Main windows size and location
            this.rootLayout = new Rectangle(Integer.parseInt(properties.getProperty("primaryStageX")),
                    Integer.parseInt(properties.getProperty("primaryStageY")),
                    Integer.parseInt(properties.getProperty("primaryStageWidth")),
                    Integer.parseInt(properties.getProperty("primaryStageHeight")));
        } else this.rootLayout = new Rectangle(100, 100, 1400, 400);

        if (properties.containsKey("addStageX") && properties.containsKey("addStageY") &&
                properties.containsKey("addStageWidth") && properties.containsKey("addStageHeight")){
            // Add windows size and location
            this.addLayout = new Rectangle(Integer.parseInt(properties.getProperty("addStageX")),
                    Integer.parseInt(properties.getProperty("addStageY")),
                    Integer.parseInt(properties.getProperty("addStageWidth")),
                    Integer.parseInt(properties.getProperty("addStageHeight")));
        } else this.addLayout = new Rectangle(100, 100, 700, 700);

        if (properties.containsKey("editStageX") && properties.containsKey("editStageY") &&
                properties.containsKey("editStageWidth") && properties.containsKey("editStageHeight")){
            // Edit windows size and location
            this.editLayout = new Rectangle(Integer.parseInt(properties.getProperty("editStageX")),
                    Integer.parseInt(properties.getProperty("editStageY")),
                    Integer.parseInt(properties.getProperty("editStageWidth")),
                    Integer.parseInt(properties.getProperty("editStageHeight")));
        } else this.editLayout = new Rectangle(100, 100, 700, 700);

        if (properties.containsKey("browserStageX") && properties.containsKey("browserStageY") &&
                properties.containsKey("browserStageWidth") && properties.containsKey("browserStageHeight")){
            // Edit windows size and location
            this.browserLayout = new Rectangle(Integer.parseInt(properties.getProperty("browserStageX")),
                    Integer.parseInt(properties.getProperty("browserStageY")),
                    Integer.parseInt(properties.getProperty("browserStageWidth")),
                    Integer.parseInt(properties.getProperty("browserStageHeight")));
        } else this.browserLayout = new Rectangle(300, 300, 500, 750);

        if (properties.containsKey("lastPathToDB")) {
            // last save-load path
            this.lastPathToDB = properties.getProperty("lastPathToDB");
        } else this.lastPathToDB = "";

    }

    public void saveProperties(){

        String comments = "There will be comments";
        Properties properties = new Properties();

        properties.put("lastCountry", this.lastCountry);
        properties.put("isMainMaximized", String.valueOf(this.isMainMaximized ? 1 : 0));

        properties.put("primaryStageX", String.valueOf((int)this.rootLayout.getX()));
        properties.put("primaryStageY", String.valueOf((int)this.rootLayout.getY()));
        properties.put("primaryStageWidth", String.valueOf((int)this.rootLayout.getWidth()));
        properties.put("primaryStageHeight", String.valueOf((int)this.rootLayout.getHeight()));

        properties.put("addStageX", String.valueOf((int)this.addLayout.getX()));
        properties.put("addStageY", String.valueOf((int)this.addLayout.getY()));
        properties.put("addStageWidth", String.valueOf((int)this.addLayout.getWidth()));
        properties.put("addStageHeight", String.valueOf((int)this.addLayout.getHeight()));

        properties.put("editStageX", String.valueOf((int)this.editLayout.getX()));
        properties.put("editStageY", String.valueOf((int)this.editLayout.getY()));
        properties.put("editStageWidth", String.valueOf((int)this.editLayout.getWidth()));
        properties.put("editStageHeight", String.valueOf((int)this.editLayout.getHeight()));

        properties.put("browserStageX", String.valueOf((int)this.browserLayout.getX()));
        properties.put("browserStageY", String.valueOf((int)this.browserLayout.getY()));
        properties.put("browserStageWidth", String.valueOf((int)this.browserLayout.getWidth()));
        properties.put("browserStageHeight", String.valueOf((int)this.browserLayout.getHeight()));

        properties.put("lastPathToDB", this.lastPathToDB);

        try (OutputStream output = new FileOutputStream("config.properties")){
            properties.store(output, comments);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
