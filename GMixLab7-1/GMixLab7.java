/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmixlab7;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.Preferences;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Gingervitis
 */
public class GMixLab7 extends Application{
    
    private boolean mDialogShowing = false;
    private Label mStatus;
    private Text mText;
    private BorderPane mRoot;
    private OptionsLayoutController mController;
    private Preferences mPref = Preferences.userNodeForPackage(getClass());
    private boolean mOpen = false;

    @Override
    public void start(Stage primaryStage) throws IOException {
        if (mPref.getBoolean("FirstRun", true) == true){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("First Run");
            alert.setHeaderText("Hello First Time User");
            alert.setContentText("Please visit File -> Options");
            alert.showAndWait();
            mPref.putBoolean("FirstRun", false);
        }
        mController = new OptionsLayoutController();
        mRoot = new BorderPane();
        reflectPreferences();
        mPref.addPreferenceChangeListener(prefChangeEvent -> onPrefChange(prefChangeEvent));

        // add the menus
        mRoot.setTop(buildMenuBar());
        mStatus = new Label("Everything is copacetic");
        ToolBar toolBar = new ToolBar(mStatus);
        mRoot.setBottom(toolBar);

        Scene scene = new Scene(mRoot, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Graham Mix, CSCD 370 Lab 7, Winter 2020");
        alert.showAndWait();
    }

    private MenuBar buildMenuBar() {
        // build a menu bar
        MenuBar menuBar = new MenuBar();
        // File menu with just a quit item for now
        Menu fileMenu = new Menu("_File");
        MenuItem optionsMenuItem = new MenuItem("_Options");
        optionsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O,
                KeyCombination.CONTROL_DOWN));
        optionsMenuItem.setOnAction(actionEvent -> onOptions());
        fileMenu.getItems().add(optionsMenuItem);
        MenuItem quitMenuItem = new MenuItem("_Quit");
        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q,
                KeyCombination.CONTROL_DOWN));
        quitMenuItem.setOnAction(actionEvent -> Platform.exit());
        fileMenu.getItems().add(quitMenuItem);
        // Help menu with just an about item for now
        Menu helpMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);
        menuBar.getMenus().addAll(fileMenu, helpMenu);
        return menuBar;
    }

    private void setStatus(String content) {
        if (content == null || content.contentEquals("")) {
            mStatus.setText("Empty");
        } else {
            mStatus.setText(content);
        }
    }
    
    private void onOptions(){
        if (mOpen){
            mController.show();
        }
        else {
            mController.initModality(Modality.NONE);
            mController.show();
            mOpen = true;
        }
    }
    
    private void reflectPreferences(){
        if (!mController.mSettings.getDateDisplayed()) {
            mText = new Text(mController.mSettings.getString());
        } else if (mController.mSettings.getDateDisplayed()) {
            SimpleDateFormat dateYear = new SimpleDateFormat("MM-dd-yy");
            SimpleDateFormat dateTime = new SimpleDateFormat("hh:mm:ss");
            Date date = new Date();
            mText = new Text("Date: " + dateYear.format(date) + "  Time: " + dateTime.format(date));
        }
        
        Font font = Font.font("Times New Roman", mController.mSettings.getFontSize());
        if (mController.mSettings.getItalicsOn() && mController.mSettings.getBoldOn()) {
            font = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, mController.mSettings.getFontSize());
        } else if (mController.mSettings.getItalicsOn()) {
            font = Font.font("Times New Roman", FontPosture.ITALIC, mController.mSettings.getFontSize());
        } else if (mController.mSettings.getBoldOn()) {
            font = Font.font("Times New Roman", FontWeight.BOLD, mController.mSettings.getFontSize());
        }
        mText.setFont(font);
        mRoot.setCenter(mText);
    }
    
    private void onPrefChange(PreferenceChangeEvent pce){
        Platform.runLater(() -> { reflectPreferences(); });
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}