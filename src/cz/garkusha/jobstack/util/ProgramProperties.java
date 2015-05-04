package cz.garkusha.jobstack.util;

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
            // web page lastCountry
            this.lastCountry = properties.getProperty("lastCountry") != null ? properties.getProperty("lastCountry") : "Czech";
            // fullscreen main window
            this.isMainMaximized = "1".equals(properties.getProperty("isMainMaximized"));
        } catch (IOException e) {
            System.out.println("Can't find config file, I'll create new");
            this.lastCountry = "Czech";
        }
    }

    public void saveProperties(){

        String comments = "There will be comments";
        Properties properties = new Properties();

        properties.put("lastCountry", this.lastCountry);
        properties.put("isMainMaximized", String.valueOf(this.isMainMaximized ? 1 : 0));
/*TODO*/
        try (OutputStream output = new FileOutputStream("config.properties")){
            properties.store(output, comments);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
