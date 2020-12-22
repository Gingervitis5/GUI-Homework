/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmixhw3;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author Gingervitis
 */
public class GMixHW3 extends Application {
    
    private static Label mStatus;
    private Panel mPanel1, mPanel2;
    private BorderPane mRoot;
    private Scene mScene;
    private StyleSheet mStyle = new StyleSheet();
    private ToggleGroup mToggle = new ToggleGroup();

    @Override
    public void start(Stage primaryStage) throws IOException {
        mPanel1 = new Panel(Color.RED, "Panel 1");
        mPanel2 = new Panel(Color.AQUA, "Panel 2");
        
        mRoot = new BorderPane();
        VBox vbox = new VBox(mPanel1, mPanel2);
        
        mRoot.setCenter(vbox);
        VBox.setVgrow(mPanel1, Priority.ALWAYS);
        VBox.setVgrow(mPanel2, Priority.ALWAYS);
        // add the menus
        mRoot.setTop(buildMenuBar());
        mStatus = new Label("Everything is copacetic");
        ToolBar toolBar = new ToolBar(mStatus);
        mRoot.setBottom(toolBar);

        Scene mScene = new Scene(mRoot, 500, 300);

        primaryStage.setTitle("Clipboard");
        primaryStage.setScene(mScene);
        primaryStage.getIcons().add(new Image("pepe.jpg"));
        primaryStage.show();
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Graham Mix, CSCD 370 HW 3, Winter 2020");
        alert.showAndWait();
    }

    private MenuBar buildMenuBar() {
        // build a menu bar
        MenuBar menuBar = new MenuBar();
        // File menu with just a quit item for now
        Menu fileMenu = new Menu("_File");
        MenuItem quitMenuItem = new MenuItem("_Quit");
        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q,
                KeyCombination.CONTROL_DOWN));
        quitMenuItem.setOnAction(actionEvent -> Platform.exit());
        fileMenu.getItems().add(quitMenuItem);
        //View menu
        Menu viewMenu = new Menu("View");
        RadioMenuItem javafx = new RadioMenuItem("JavaFX");
        javafx.setOnAction(event -> onJavaFX());
        javafx.setToggleGroup(mToggle);
        viewMenu.getItems().add(javafx);
        RadioMenuItem darkMode = new RadioMenuItem("Dark Mode");
        darkMode.setOnAction(event -> onDark());
        darkMode.setToggleGroup(mToggle);
        viewMenu.getItems().add(darkMode);
        // Help menu with just an about item for now
        Menu helpMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);
        menuBar.getMenus().addAll(fileMenu, viewMenu, helpMenu);
        return menuBar;
    }

    public static void setStatus(String content) {
        if (content == null || content.contentEquals("")) {
            mStatus.setText("Empty");
        } else {
            mStatus.setText(content);
        }
    }
    
    private void onJavaFX(){
        mRoot.getStylesheets().add(
         getClass()
        .getResource("JavaFX.css")
        .toExternalForm());
        mRoot.setStyle("JavaFX.css");
        mRoot.setStyle("-fx-background-color: white;") ;
    }
    
    private void onDark(){
        mRoot.getStylesheets().add(
         getClass()
        .getResource("DarkMode.css")
        .toExternalForm());
        mRoot.setStyle("-fx-background-color: lightgray;") ;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
