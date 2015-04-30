package cz.garkusha.jobstack;
/**
 *  Main application javaFX class.
 *
 * @author Konstantin Garkusha
 */
import java.io.*;

import cz.garkusha.jobstack.model.Position;
import cz.garkusha.jobstack.util.*;
import cz.garkusha.jobstack.view.*;

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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {
    private boolean isDataChanged;
    private Stage primaryStage;
    private Stage addDialogStage;
    private BorderPane rootLayout;
    private final DBCommunication dbCommunication;
    private ProgramProperties programProperties;
    RootLayoutController rootController;

    /**
     * The data as an observable list of Positions.
     */
    private final ObservableList<Position> positions = FXCollections.observableArrayList();

    public MainApp() {
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

    public void setDataChanged(boolean isDataChanged) {
        this.isDataChanged = isDataChanged;
    }

    public void saveToDB(){
        // delete pdf file, which were deleted from table
        DeletePositionsPDF.deletedPDFDelete();

        dbCommunication.writePositionsToDB();
        //Zip database to JobStack *.dat file
        ZipDB.compression();
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
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JobStack");
        // load icon
        try ( InputStream is = getClass().getResourceAsStream("/cz/garkusha/jobstack/resources/icon.png")){
            Image image = new Image(is);
            this.primaryStage.getIcons().add(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                    DeletePositionsPDF.unsavedPDFDelete();
                    primaryStage.close();
                }
            } else {
                primaryStage.close();
            }
            programProperties.setMainMaximized(primaryStage.isMaximized());
            programProperties.saveProperties();
        });
    }

    @Override
    public void stop(){
        primaryStage.close();
    }

    /**
     * Initializes the root layout.
     */
    void initRootLayout() {
        System.out.println("All it's ok, start working...");
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
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
        try {
            // Load table layout.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TableLayout.fxml"));
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
     * @return the main stage.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
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
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PositionAddDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            addDialogStage = new Stage();
            addDialogStage.setTitle("Add position");
            addDialogStage.initModality(Modality.WINDOW_MODAL);
            addDialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            addDialogStage.setScene(scene);

            // Set the position into the addController.
            PositionAddDialogController addController = loader.getController();
            addController.setDialogStage(addDialogStage);
            addController.setPosition(position);
            addController.setMainApp(this);

            // Show the dialog and wait until the user closes it
            addDialogStage.showAndWait();

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
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PositionEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit position");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the position into the editController.
            PositionEditDialogController editController = loader.getController();
            editController.setDialogStage(dialogStage);
            editController.setPosition(position);
            editController.setMainApp(this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

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
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PerhapsTheSamePosition.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            SplitPane splitPane = (SplitPane)page.getChildren().get(1);
            AnchorPane anchorPane = (AnchorPane)splitPane.getItems().get(1);
            anchorPane.getChildren().add(SamePositionTabPaneLayout());
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Be careful, you already sent your application to this company.");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(addDialogStage);
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
    public TabPane SamePositionTabPaneLayout() {

        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SamePositionTabPane.fxml"));
            TabPane page = (TabPane) loader.load();

            //Create tabs for each same positions
            for (Position samePosition : FindProbablyTheSamePositions.getProbablyTheSamePositionList()) {
                FXMLLoader tabLoader = new FXMLLoader();
                tabLoader.setLocation(MainApp.class.getResource("view/SamePositionTab.fxml"));
                Tab tab = new Tab();
                tab.setContent(tabLoader.load());
                // add tabs to TabPane
                page.getTabs().add(tab);
                SamePositionTabController samePositionTabController = tabLoader.getController();
                samePositionTabController.setOldPosition(tab, samePosition);
            }
            return page;
        } catch (IOException e) {
            e.printStackTrace();
        }
       return new TabPane();
    }
}