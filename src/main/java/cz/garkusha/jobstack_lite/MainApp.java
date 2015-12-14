package cz.garkusha.jobstack_lite;
/**
 *
 * It's lite version of JobStack.
 * JobStack EE will be working with Google MySQL Cloud ana Hibernate
 *  Main application javaFX class.
 *
 * @author Konstantin Garkusha
 */
import java.awt.*;
import java.io.*;

import cz.garkusha.jobstack_lite.model.Position;
import cz.garkusha.jobstack_lite.util.*;
import cz.garkusha.jobstack_lite.controller.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(MainApp.class);

    private boolean isDataChanged;
    private Stage primaryStage;
    private Stage addStage;
    private Stage editStage;
    private Stage browserStage;
    private BorderPane rootLayout;
    private final DBCommunication dbCommunication;
    private ProgramProperties programProperties;
    RootLayoutController rootController;

    /**
     * The data as an observable list of Positions.
     */
    private final ObservableList<Position> positions = FXCollections.observableArrayList();

    public MainApp() {
        LOG.info("Main constructor");
        this.dbCommunication = new DBCommunication(positions);
        this.isDataChanged = false;
        // Load properties
        this.programProperties = ProgramProperties.getInstance();
    }

     /**
     * Returns the data as an observable list of Positions.
     * @return observable list of Positions
     */
    public ObservableList<Position> getPositions() {
        return positions;
    }

    /**
     * @return the main stage.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setDataChanged(boolean isDataChanged) {
        this.isDataChanged = isDataChanged;
    }

    public void saveToDB(){
        dbCommunication.writePositionsToDB();
        //Zip database to JobStack *.dat file
        ZipDB.compression();
        LOG.debug("Data base was saved to file");
    }

    public int getPositionsMaxId(){
        int max = 0;
        for (Position p : positions){
            max = p.getId() > max ? p.getId() : max;
        }
        return max;
    }

    @Override
    public void start(Stage primaryStage) {
        LOG.debug("Setting properties to main window");
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JobStack");
        //Set size from properties
        this.primaryStage.setX(programProperties.getRootLayout().getX());
        this.primaryStage.setY(programProperties.getRootLayout().getY());
        this.primaryStage.setWidth(programProperties.getRootLayout().getWidth());
        this.primaryStage.setHeight(programProperties.getRootLayout().getHeight());

        // load icons
        try ( InputStream is_32  = getClass().getResourceAsStream("/icons/icon_32.png");
              InputStream is_64  = getClass().getResourceAsStream("/icons/icon_64.png");
              InputStream is_128 = getClass().getResourceAsStream("/icons/icon_128.png");
              InputStream is_256 = getClass().getResourceAsStream("/icons/icon_256.png");
              InputStream is_640 = getClass().getResourceAsStream("/icons/icon_640.png")
        ){
            this.primaryStage.getIcons().add(new Image(is_32));
            this.primaryStage.getIcons().add(new Image(is_64));
            this.primaryStage.getIcons().add(new Image(is_128));
            this.primaryStage.getIcons().add(new Image(is_256));
            this.primaryStage.getIcons().add(new Image(is_640));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOG.debug("Properties to main window was set");
        initRootLayout();
        showTableLayout();
        // Dialog when you press close button
        this.primaryStage.setOnCloseRequest(event -> {
            event.consume();
            if (isDataChanged){
                String result = Dialogs.saveToDBConfirmation();
                if (result.equals("YES")){
                    saveToDB();
                    primaryStage.close();
                } else if (result.equals("NO")) {
                    primaryStage.close();
                }
            } else {
                primaryStage.close();
            }
            /** Save properties*/
            programProperties.setMainMaximized(primaryStage.isMaximized());
            programProperties.setRootLayout(new Rectangle((int) this.primaryStage.getX(), (int) this.primaryStage.getY(),
                    (int) this.primaryStage.getWidth(), (int) this.primaryStage.getHeight()));
            programProperties.saveProperties();
            LOG.debug("All properties was save");
        });
    }

    @Override
    public void stop(){
        LOG.debug("Main window was closed");
        primaryStage.close();
    }

    /**
     * Initializes the root layout.
     */
    void initRootLayout() {
        LOG.info("InitRootLayout starts");
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setMaximized(programProperties.isMainMaximized());
            primaryStage.show();
            rootController = loader.getController();
            rootController.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the table layout inside the root layout.
     */
    void showTableLayout() {
        LOG.info("TableLayout starts");
        try {
            // Load table layout.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/TableLayout.fxml"));
            AnchorPane tableLayout = (AnchorPane) loader.load();

            // Set table layout into the center of root layout.
            rootLayout.setCenter(tableLayout);


            // Give the controller access to the main app.
            TableLayoutController tableController = loader.getController();
            tableController.setMainApp(this);
            // add reference of tableController to rootController
            rootController.setTableLayoutController(tableController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to add details for the specified position. If the user
     * clicks OK, the changes are saved into the provided position object and true
     * is returned.
     *
     * @param position the position object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showPositionAddDialog(Position position) {
        LOG.info("PositionAddDialog starts");
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/PositionAddDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            addStage = new Stage();
            addStage.setTitle("Add position");
            addStage.initModality(Modality.WINDOW_MODAL);
            addStage.initOwner(primaryStage);
            addStage.setX(programProperties.getAddLayout().getX());
            addStage.setY(programProperties.getAddLayout().getY());
            addStage.setWidth(programProperties.getAddLayout().getWidth());
            addStage.setHeight(programProperties.getAddLayout().getHeight());
            Scene scene = new Scene(page);
            addStage.setScene(scene);

            // Set the position into the addController.
            PositionAddDialogController addController = loader.getController();
            addController.setDialogStage(addStage);
            addController.setPosition(position);
            addController.setMainApp(this);

            // Show the dialog and wait until the user closes it
            addStage.showAndWait();

            programProperties.setAddLayout(new Rectangle((int) addStage.getX(), (int) addStage.getY(),
                    (int) addStage.getWidth(), (int) addStage.getHeight()));

            return addController.isSaveClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Opens a dialog to add details for the specified position. If the user
     * clicks OK, the changes are saved into the provided position object and true
     * is returned.
     *
     * @param position the position object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showPositionEditDialog(Position position) {
        LOG.info("PositionEditDialog starts");
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/PositionEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            editStage = new Stage();
            editStage.setTitle("Edit position");
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.initOwner(primaryStage);
            editStage.setX(programProperties.getEditLayout().getX());
            editStage.setY(programProperties.getEditLayout().getY());
            editStage.setWidth(programProperties.getEditLayout().getWidth());
            editStage.setHeight(programProperties.getEditLayout().getHeight());
            Scene scene = new Scene(page);
            editStage.setScene(scene);

            // Set the position into the editController.
            PositionEditDialogController editController = loader.getController();
            editController.setDialogStage(editStage);
            editController.setPosition(position);
            editController.setMainApp(this);

            // Show the dialog and wait until the user closes it
            editStage.showAndWait();

            programProperties.setEditLayout(new Rectangle((int) editStage.getX(), (int) editStage.getY(),
                    (int) editStage.getWidth(), (int) editStage.getHeight()));

            return editController.isSaveClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Shows the Probably The Same Position layout.
     */
    public void showProbablyTheSamePositionLayout(Position filledPosition) {
        LOG.info("ProbablyTheSamePositionLayout starts");
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/PerhapsTheSamePosition.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            SplitPane splitPane = (SplitPane)page.getChildren().get(1);
            AnchorPane anchorPane = (AnchorPane)splitPane.getItems().get(1);
            anchorPane.getChildren().add(SamePositionTabPaneLayout(filledPosition));
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Be careful, you already sent your application to this company.");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(addStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the position into the addController.
            PerhapsTheSamePositionController sameController = loader.getController();
            sameController.setDialogStage(dialogStage);
            sameController.setNewPosition(filledPosition);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * create new TabPane and add some Tab to his.
     */
    public TabPane SamePositionTabPaneLayout(Position filledPosition) {
        LOG.info("SamePositionTabPaneLayout starts");

        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/SamePositionTabPane.fxml"));
            TabPane page = (TabPane) loader.load();

            //Create tabs for each same positions
            for (Position samePosition : FindProbablyTheSamePositions.getProbablyTheSamePositionList()) {
                FXMLLoader tabLoader = new FXMLLoader();
                tabLoader.setLocation(MainApp.class.getResource("/fxml/SamePositionTab.fxml"));
                Tab tab = new Tab();
                tab.setContent(tabLoader.load());
                // add tabs to TabPane
                page.getTabs().add(tab);
                SamePositionTabController samePositionTabController = tabLoader.getController();
                samePositionTabController.setOldPosition(tab, filledPosition, samePosition);
            }
            return page;
        } catch (IOException e) {
            e.printStackTrace();
        }
       return new TabPane();
    }

    /**
     * Shows the Internet Browser.
     */
    public void showInternetBrowser(String urlOrHtml, String title) {
        LOG.info("Internet Browser starts");

        // Create the dialog Stage.
        browserStage = new Stage();
        browserStage.initModality(Modality.WINDOW_MODAL);

        // create the scene
        browserStage.setTitle(title);
        browserStage.setX(programProperties.getBrowserLayout().getX());
        browserStage.setY(programProperties.getBrowserLayout().getY());
        browserStage.setWidth(programProperties.getBrowserLayout().getWidth());
        browserStage.setHeight(programProperties.getBrowserLayout().getHeight());

        Scene scene = new Scene(new Browser(urlOrHtml),750,500, Color.web("#666970"));
        browserStage.setScene(scene);

        // Show the dialog and wait until the user closes it
        browserStage.showAndWait();

        programProperties.setBrowserLayout(new Rectangle((int) browserStage.getX(), (int) browserStage.getY(),
                (int) browserStage.getWidth(), (int) browserStage.getHeight()));

    }
}