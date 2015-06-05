package cz.garkusha.jobstack_lite.util;

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
        System.out.println(lastCountry);
    }

    public Rectangle getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(Rectangle rootLayout) {
        this.rootLayout = rootLayout;
    }

    public boolean isMainMaximized() {
        return isMainMaximized;
    }

    public void setMainMaximized(boolean mainMaximized) {
        this.isMainMaximized = mainMaximized;
    }

    private void loadProperties(){
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Can't find config file, I'll create new");
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

/*TODO*/
        try (OutputStream output = new FileOutputStream("config.properties")){
            properties.store(output, comments);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
