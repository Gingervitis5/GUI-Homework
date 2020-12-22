/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ashman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Gingervitis
 */
public class Main extends Application implements Serializable{
    
    private static Game mGame = new Game();
    private static Label mLevelText = new Label();
    private static Label mText = new Label();
    private static Label mScore = new Label();
    private static ToolBar toolBar;
    private static MenuItem goMenuItem, pauseMenuItem, saveMenuItem, openMenuItem;
    private Preferences mGhostPrefs = Preferences.userNodeForPackage(getClass());
    private File mSaveFile;
    private Stage mStage;
    
    @Override
    public void start(Stage primaryStage) {
              
        mStage = primaryStage;
        
        BorderPane borderpane = new BorderPane();
        borderpane.setCenter(mGame);
        MenuBar menuBar = buildMenuBar();
        borderpane.setTop(menuBar);
        
        toolBar = buildToolBar();
        borderpane.setBottom(toolBar);
        
        mGhostPrefs.addPreferenceChangeListener(prefChangeEvent -> onPrefChange(prefChangeEvent));
        mGame.mSettings.setNumGhosts(2);
        mGame.mSettings.setSpeed(1);
        mGame.reflectPreferences(false);
        
        Scene scene = new Scene(borderpane, mGame.getCanvasWidth(), mGame.getCanvasHeight());
        scene.setOnKeyPressed(keyEvent -> onKeyPressed(keyEvent));
        
        primaryStage.setTitle("Ashman");
        primaryStage.sizeToScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void update(){
        mLevelText.setText("Level " + mGame.getLevel() + "  ");
        mScore.setText("Score: " + mGame.getScore() + "  ");
        mText.setText("Number of Cakes left: " + mGame.getCakes());
    }
    
    private MenuBar buildMenuBar() {
        // build a menu bar
        MenuBar menuBar = new MenuBar();
        // File menu with just a quit item for now
        Menu fileMenu = new Menu("_File");
        fileMenu.setAccelerator(new KeyCodeCombination(KeyCode.F,
                KeyCombination.CONTROL_DOWN));
        saveMenuItem = new MenuItem("Sa_ve");
        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.V,
                KeyCombination.CONTROL_DOWN));
        saveMenuItem.setOnAction(event -> {
            try {
                onSave();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        saveMenuItem.setDisable(false);
        openMenuItem = new MenuItem("Op_en");
        openMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.E,
                KeyCombination.CONTROL_DOWN));
        openMenuItem.setOnAction(event -> onOpen());
        openMenuItem.setDisable(false);
        MenuItem quitMenuItem = new MenuItem("_Quit");
        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q,
                KeyCombination.CONTROL_DOWN));
        quitMenuItem.setOnAction(actionEvent -> Platform.exit());
        fileMenu.getItems().addAll(saveMenuItem, openMenuItem, quitMenuItem);
        //Game menu
        Menu gameMenu = new Menu("_Game");
        gameMenu.setAccelerator(new KeyCodeCombination(KeyCode.G,
                KeyCombination.CONTROL_DOWN));
        MenuItem newMenuItem = new MenuItem("_New");
        newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N,
                KeyCombination.CONTROL_DOWN));
        newMenuItem.setOnAction(event -> onNew());
        gameMenu.getItems().add(newMenuItem);
        SeparatorMenuItem separator = new SeparatorMenuItem();
        gameMenu.getItems().add(separator);
        goMenuItem = new MenuItem("G_o");
        goMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O,
                KeyCombination.CONTROL_DOWN));
        goMenuItem.setOnAction(event -> onGo());
        gameMenu.getItems().add(goMenuItem);
        pauseMenuItem = new MenuItem("_Pause");
        pauseMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.P,
                KeyCombination.CONTROL_DOWN));
        pauseMenuItem.setOnAction(event -> onPause());
        pauseMenuItem.setDisable(true);
        gameMenu.getItems().add(pauseMenuItem);
        MenuItem settingsMenuItem = new MenuItem("_Settings");
        settingsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S,
                KeyCombination.CONTROL_DOWN));
        settingsMenuItem.setOnAction(event -> onSettings());
        gameMenu.getItems().add(settingsMenuItem);
        // Help menu with just an about item for now
        Menu helpMenu = new Menu("_Help");
        helpMenu.setAccelerator(new KeyCodeCombination(KeyCode.H,
                KeyCombination.CONTROL_DOWN));
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.A,
                KeyCombination.CONTROL_DOWN));
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);
        menuBar.getMenus().addAll(fileMenu, gameMenu, helpMenu);
        return menuBar;
    }
    
    private ToolBar buildToolBar(){
        ToolBar toolBar = new ToolBar();
        mLevelText = new Label("Level " + mGame.getLevel() + "  ");
        mScore = new Label("Score: " + mGame.getScore() + "  ");
        mText = new Label("Number of Cakes left: " + mGame.getCakes());
        toolBar.getItems().addAll(mLevelText, mScore, mText);
        return toolBar;
    }
    
    public Label getLLevelText(){
        return mLevelText;   
    }
    
    public Label getText(){
        return mText;   
    }
    
    public static Game getGame(){
        return mGame;   
    }
    
    public void onAbout(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Graham Mix, CSCD 370 Project, Winter 2020");
        alert.showAndWait();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getCode()){
            case UP:
                Game.ashman.setDirection(Direction.UP);
                break;
            case DOWN:
                Game.ashman.setDirection(Direction.DOWN);
                break;
            case LEFT:
                Game.ashman.setDirection(Direction.LEFT);
                break;
            case RIGHT:
                Game.ashman.setDirection(Direction.RIGHT);
                break;
            case HOME:
                mGame.cheat();
                update();
        }
    }

    private void onGo() {
        Game.getTimer().start();
        goMenuItem.setDisable(true);
        pauseMenuItem.setDisable(false);
        openMenuItem.setDisable(true);
        saveMenuItem.setDisable(true);
        
    }
    
     private void onNew(){
        mGame.reset();
        mScore.setText("Score: " + mGame.getScore() + "  ");
        mText.setText("Number of Cakes left: " + mGame.getCakes());
        openMenuItem.setDisable(false);
        saveMenuItem.setDisable(false);
        Game.ashman.setDirection(Direction.NONE);
        Game.ashman.setCoordinates(1, 1);
    }
    
    public static void onPause() {
        Game.getTimer().stop();
        goMenuItem.setDisable(false);
        pauseMenuItem.setDisable(true);
        openMenuItem.setDisable(false);
        saveMenuItem.setDisable(false);
    }
    
    private void onPrefChange(PreferenceChangeEvent pce){
        Platform.runLater(() -> { mGame.reflectPreferences(true); });
    }

    private void onSettings() {
        Game.getTimer().stop();
        if (mGame.getLevel() == 1){
            Alert deny = new Alert(Alert.AlertType.ERROR);
            deny.setTitle("Error");
            deny.setHeaderText("Changing settings only allowed in level 2");
            deny.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ghost Settings");
            alert.setHeaderText("Select your Ghost settings");
            alert.getDialogPane().setContentText("Number of Ghosts");

            Label l1 = new Label("Number of Starting Ghosts");
            ChoiceBox<Integer> c1 = new ChoiceBox<>();
            c1.getItems().addAll(1, 2, 3, 4);
            c1.setValue(mGhostPrefs.getInt("Ghosts", 1));
            HBox hbox1 = new HBox(l1, c1);
            hbox1.setAlignment(Pos.CENTER_LEFT);
            hbox1.setSpacing(10.0);
            Label l2 = new Label("Number of Additional Ghosts");
            ChoiceBox<Integer> c2 = new ChoiceBox<>();
            c2.getItems().addAll(0, 1, 2, 3, 4);
            c2.setValue(mGhostPrefs.getInt("AddGhosts", 1));
            HBox hbox2 = new HBox(l2, c2);
            hbox1.setAlignment(Pos.CENTER_LEFT);
            hbox1.setSpacing(10.0);
            Label l3 = new Label("Speed of Ghosts");
            ChoiceBox<Integer> c3 = new ChoiceBox<>();
            c3.getItems().addAll(1, 2);
            c3.setValue(mGhostPrefs.getInt("Speed", 1));
            HBox hbox3 = new HBox(l3, c3);
            hbox3.setAlignment(Pos.CENTER_LEFT);
            hbox3.setSpacing(10.0);
            VBox vbox = new VBox(2.0, hbox1, hbox2, hbox3, new Label("Please select Game->Go after selection"));
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(4.0);
            alert.getDialogPane().setContent(vbox);
            alert.showAndWait();
            int numGhosts = c1.getValue();
            int numAddGhosts = c2.getValue();
            int speed = c3.getValue();
            mGame.mSettings.setNumGhosts(numGhosts);
            mGame.mSettings.setNumAddGhosts(numAddGhosts);
            mGame.mSettings.setSpeed(speed);
            mGame.mSettings.storePreferences(getClass());
        }
        onPause();
    }
    
    private void onSave() throws IOException{
        File selectedFile = mSaveFile;
        if (selectedFile == null){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            if (mSaveFile != null){
                fileChooser.setInitialFileName(mSaveFile.getName());
            }
            else if (selectedFile == null){
                selectedFile = fileChooser.showSaveDialog(mStage);
            }
        }
        if (selectedFile != null){
            FileOutputStream fstream = new FileOutputStream(selectedFile);
            ObjectOutputStream ostream = new ObjectOutputStream(fstream);
            ostream.writeObject(mGame);
            ostream.close();
            mSaveFile = selectedFile;
        }
    }
    
    private void onOpen(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file");
        File selectedFile = fileChooser.showOpenDialog(mStage);
        if (selectedFile == null) {
            return;
        }
        try {
            FileInputStream fis = new FileInputStream(selectedFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            mGame = (Game)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.getStackTrace();
        }
        mSaveFile = selectedFile;
        mStage.setTitle(mSaveFile.getName());
    }
}
