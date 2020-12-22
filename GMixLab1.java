/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmixlab1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Gingervitis
 */

public class GMixLab1 extends Application {

    private Label mStatus;
    private ImageView mImage;
    private ChangeListener<String> listener = new MyChangeListener();
    
    class MyChangeListener implements ChangeListener<String> {

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        Image newImage;
        if (newValue.equals("First Album")){
            newImage = new Image("B52s/logo.png");
            setStatus("First Album, 1979");
        }
        else if (newValue.equals("Cindy")){
            newImage = new Image("B52s/cindy.png");
            setStatus("Cindy Wilson (Percussion since 1976)");
        }
        else if (newValue.equals("Fred")){
            newImage = new Image("B52s/fred.png");
            setStatus("Fred Schneider (Vocals, Cowbell since 1976)");
        }
        else if (newValue.equals("Kate")){
            newImage = new Image("B52s/kate.png");
            setStatus("Kate Pierson (Organ since 1976)");
        }
        else if (newValue.equals("Keith")){
            newImage = new Image("B52s/keith.png");
            setStatus("Keith Strickland (Drums, Guitar, since 1976)");
        }
        else if (newValue.equals("Matt")){
            newImage = new Image("B52s/matt.png");
            setStatus("Matt Flynn (Touring, Drums, prior to 2004)");
        }
        else if (newValue.equals("Ricky")){
            newImage = new Image("B52s/rickey.png");
            setStatus("Ricky Wilson, deceased (Bass, 1976-1985)");
        }
        else {
            newImage = new Image("B52s/logo.png");
            setStatus("First Album, 1979");
        }
        mImage.setImage(newImage);
    }
    
}

    @Override
    public void start(Stage primaryStage) {
        
        Image image = new Image("B52s/logo.png");
        mImage = new ImageView(image);

        BorderPane root = new BorderPane();
        root.setCenter(mImage);
        
        ObservableList<String> items = FXCollections.observableArrayList("First Album", "Cindy", "Fred", "Kate",
                                                                        "Keith", "Matt", "Ricky");
        ListView<String> list = new ListView();
        list.setItems(items);
        root.setLeft(list);
        list.getSelectionModel().selectedItemProperty().addListener(this.listener);
        list.setPrefWidth(computeStringWidth("00000000000"));

        // add the menus
        root.setTop(buildMenuBar());
        this.mStatus = new Label("Everything is copacetic");
        ToolBar toolBar = new ToolBar(this.mStatus);
        root.setBottom(toolBar);

        Scene scene = new Scene(root);

        primaryStage.setTitle("B52's");
        primaryStage.minWidthProperty().bind(scene.widthProperty().add(50));
        primaryStage.minHeightProperty().bind(scene.heightProperty());
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About the B52's");
        alert.setHeaderText("Graham Mix, CSCD 370 Lab 1, Winter 2020");
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
    

    public void setStatus(String content) {
        if (content == null || content.contentEquals("")) {
            mStatus.setText("Empty");
        } else {
            mStatus.setText(content);
        }
    }
      
     private double computeStringWidth(String s) {
        // put it in a Text object
        final Text text = new Text(s);
        // now get the width
        return text.getLayoutBounds().getWidth();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
