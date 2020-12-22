/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmixlab3;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Gingervitis
 */
public class GMixLab3 extends Application {
    
    private Label mStatus;

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();
        ArrayList<SevenSegment> segArr = new ArrayList<SevenSegment>();
        SevenSegment s0 = new SevenSegment(0);
        SevenSegment s1 = new SevenSegment(1);
        SevenSegment s2 = new SevenSegment(2);
        SevenSegment s3 = new SevenSegment(3);
        SevenSegment s4 = new SevenSegment(4);
        segArr.add(s0); segArr.add(s1); segArr.add(s2); segArr.add(s3); segArr.add(s4);
        
         Button btn = new Button();
        btn.setText("Incr");
        btn.setPrefWidth(50);
        btn.setOnAction((ActionEvent event)
                -> increment(s0.getMValueOn(), segArr)
        );
        
        HBox horizBox = new HBox(4);
        horizBox.setPrefWidth(s1.getPrefWidth());
        horizBox.setPrefHeight(s1.getPrefHeight());
        horizBox.getChildren().addAll(s1,s2,s3,s4);
        horizBox.alignmentProperty().setValue(Pos.CENTER);
        VBox verticalBox = new VBox(4.0, s0, btn, horizBox);
        verticalBox.setPrefHeight(s0.getPrefHeight());
        verticalBox.setPrefWidth(s0.getPrefWidth());
        verticalBox.alignmentProperty().setValue(Pos.CENTER);
        root.setCenter(verticalBox);


        // add the menus
        root.setTop(buildMenuBar());
        mStatus = new Label("Everything is copacetic");
        ToolBar toolBar = new ToolBar(mStatus);
        root.setBottom(toolBar);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Seven Segment Display");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Graham Mix, CSCD 370 Lab 3, Winter 2020");
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
    
    private void increment(int valueOn, ArrayList<SevenSegment> segArr){
        for (int i = 0; i < segArr.size(); i++){
            segArr.get(i).setMValueOn((valueOn+1+i)%11);
        }
        for (SevenSegment segment : segArr){
            segment.draw();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
