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

    private String country;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
        System.out.println(country);
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
            // web page country
            this.country = properties.getProperty("country") != null ? properties.getProperty("country") : "Czech";
            // fullscreen main window
            this.isMainMaximized = "1".equals(properties.getProperty("isMainMaximized"));
        } catch (IOException e) {
            System.out.println("Can't find config file, I'll create new");
            this.country = "Czech";
        }
    }

    public void saveProperties(){

        String comments = "There will be comments";
        Properties properties = new Properties();

        properties.put("country", this.country);
        properties.put("isMainMaximized", String.valueOf(this.isMainMaximized ? 1 : 0));

        try (OutputStream output = new FileOutputStream("config.properties")){
            properties.store(output, comments);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
