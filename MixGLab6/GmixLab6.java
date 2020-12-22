/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmixlab6;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
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
import javafx.stage.Stage;

/**
 *
 * @author Gingervitis
 */
public class GmixLab6 extends Application {

    private Label mStatus;
    private Text mText;
    private BorderPane mRoot;

    @Override
    public void start(Stage primaryStage) {
        mText = new Text("Hello");
        mRoot = new BorderPane();
        mRoot.setCenter(mText);

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
        alert.setHeaderText("Graham Mix, CSCD 370 Lab 6, Winter 2020");
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
        try{
            OptionsLayoutController controller = new OptionsLayoutController();
            Optional<ButtonType> result = controller.showAndWait();
            if (result.isPresent()){
                ButtonData data = result.get().getButtonData();
                if (data.equals(ButtonData.OK_DONE)){
                    
                    if (!controller.mSettings.getDateDisplayed()){
                        mText = new Text(controller.mSettings.getString());
                    }
                    else if (controller.mSettings.getDateDisplayed()){
                        SimpleDateFormat dateYear = new SimpleDateFormat("MM-dd-yy");
                         SimpleDateFormat dateTime = new SimpleDateFormat("hh-mm-ss");
                        Date date = new Date();                        
                        mText = new Text("Date: " + dateYear.format(date) + "  Time: " + dateTime.format(date));
                    }
                    Font font = Font.font("Times New Roman", controller.mSettings.getFontSize());
                    if (controller.mSettings.getItalicsOn() && controller.mSettings.getBoldOn()){
                        font = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, controller.mSettings.getFontSize());
                    }
                    else if (controller.mSettings.getItalicsOn()){
                        font = Font.font("Times New Roman", FontPosture.ITALIC, controller.mSettings.getFontSize()); 
                    }
                    else if (controller.mSettings.getBoldOn()){
                        font = Font.font("Times New Roman", FontWeight.BOLD, controller.mSettings.getFontSize());
                    }
                    mText.setFont(font);
                    mRoot.setCenter(mText);
                    this.setStatus("Result returns OK");
                }
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
