/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmixlab8;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Gingervitis
 */
public class GMixLab8 extends Application{
    
     private Label mStatus;
     private final Image mXImage = new Image("gmixlab8/x.png"),
                         mOImage = new Image("gmixlab8/o.png");

    @Override
    public void start(Stage primaryStage) {
        ImageView x = new ImageView(mXImage);
        x.setUserData("X");
        x.setOnDragDetected(event -> onDrag(event)); 
        ImageView o = new ImageView(mOImage);
        o.setUserData("O");
        o.setOnDragDetected(event -> onDrag(event));
        HBox hbox = new HBox(x,o);
        hbox.setAlignment(Pos.CENTER);
        
        ImageView hDivider1 = new ImageView(new Image("gmixlab8/horiz.png"));
        hDivider1.setUserData("BLANK");
        ImageView hDivider2 = new ImageView(new Image("gmixlab8/horiz.png"));
        hDivider2.setUserData("BLANK");
        ImageView hDivider3 = new ImageView(new Image("gmixlab8/horiz.png"));
        hDivider3.setUserData("BLANK");
        ImageView hDivider4 = new ImageView(new Image("gmixlab8/horiz.png"));
        hDivider4.setUserData("BLANK");
        ImageView hDivider5 = new ImageView(new Image("gmixlab8/horiz.png"));
        hDivider5.setUserData("BLANK");
        ImageView hDivider6 = new ImageView(new Image("gmixlab8/horiz.png"));
        hDivider6.setUserData("BLANK");
        
        ImageView vDivider1 = new ImageView(new Image("gmixlab8/vert.png"));
        vDivider1.setUserData("BLANK");
        ImageView vDivider2 = new ImageView(new Image("gmixlab8/vert.png"));
        vDivider2.setUserData("BLANK");
        ImageView vDivider3 = new ImageView(new Image("gmixlab8/vert.png"));
        vDivider3.setUserData("BLANK");
        ImageView vDivider4 = new ImageView(new Image("gmixlab8/vert.png"));
        vDivider4.setUserData("BLANK");
        ImageView vDivider5 = new ImageView(new Image("gmixlab8/vert.png"));
        vDivider5.setUserData("BLANK");
        ImageView vDivider6 = new ImageView(new Image("gmixlab8/vert.png"));
        vDivider6.setUserData("BLANK");
        
        ImageView blank1 = new ImageView(new Image("gmixlab8/blank.png"));
        blank1.setUserData("BLANK");
        blank1.setOnDragDropped(event -> onDragDropped(event));
        blank1.setOnDragOver(event -> onDragOver(event));
        ImageView blank2 = new ImageView(new Image("gmixlab8/blank.png"));
        blank2.setUserData("BLANK");
        blank2.setOnDragDropped(event -> onDragDropped(event));
        blank2.setOnDragOver(event -> onDragOver(event));
        ImageView blank3 = new ImageView(new Image("gmixlab8/blank.png"));
        blank3.setUserData("BLANK");
        blank3.setOnDragDropped(event -> onDragDropped(event));
        blank3.setOnDragOver(event -> onDragOver(event));
        ImageView blank4 = new ImageView(new Image("gmixlab8/blank.png"));
        blank4.setUserData("BLANK");
        blank4.setOnDragDropped(event -> onDragDropped(event));
        blank4.setOnDragOver(event -> onDragOver(event));
        ImageView blank5 = new ImageView(new Image("gmixlab8/blank.png"));
        blank5.setUserData("BLANK");
        blank5.setOnDragDropped(event -> onDragDropped(event));
        blank5.setOnDragOver(event -> onDragOver(event));
        ImageView blank6 = new ImageView(new Image("gmixlab8/blank.png"));
        blank6.setUserData("BLANK");
        blank6.setOnDragDropped(event -> onDragDropped(event));
        blank6.setOnDragOver(event -> onDragOver(event));
        ImageView blank7 = new ImageView(new Image("gmixlab8/blank.png"));
        blank7.setUserData("BLANK");
        blank7.setOnDragDropped(event -> onDragDropped(event));
        blank7.setOnDragOver(event -> onDragOver(event));
        ImageView blank8 = new ImageView(new Image("gmixlab8/blank.png"));
        blank8.setUserData("BLANK");
        blank8.setOnDragDropped(event -> onDragDropped(event));
        blank8.setOnDragOver(event -> onDragOver(event));
        ImageView blank9 = new ImageView(new Image("gmixlab8/blank.png"));
        blank9.setUserData("BLANK");
        blank9.setOnDragDropped(event -> onDragDropped(event));
        blank9.setOnDragOver(event -> onDragOver(event));
        
        GridPane playingField = new GridPane();
        playingField.setAlignment(Pos.CENTER);
        playingField.add(blank1, 0, 0);
        playingField.add(vDivider1, 1, 0);
        playingField.add(blank2, 2, 0);
        playingField.add(vDivider2, 3, 0);
        playingField.add(blank3, 4, 0);
        
        playingField.add(hDivider1, 0, 1);
        playingField.add(new Canvas(5,5), 1, 1);
        playingField.add(hDivider2, 2, 1);
        playingField.add(new Canvas(5,5), 3, 1);
        playingField.add(hDivider3, 4, 1);
        
        playingField.add(blank4, 0, 2);
        playingField.add(vDivider3, 1, 2);
        playingField.add(blank5, 2, 2);
        playingField.add(vDivider4, 3, 2);
        playingField.add(blank6, 4, 2);
        
        playingField.add(hDivider4, 0, 3);
        playingField.add(new Canvas(5,5), 1, 3);
        playingField.add(hDivider5, 2, 3);
        playingField.add(new Canvas(5,5), 3, 3);
        playingField.add(hDivider6, 4, 3);
        
        playingField.add(blank7, 0, 4);
        playingField.add(vDivider5, 1, 4);
        playingField.add(blank8, 2, 4);
        playingField.add(vDivider6, 3, 4);
        playingField.add(blank9, 4, 4);
        
        VBox vbox = new VBox(hbox, playingField);
        vbox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        // add the menus
        root.setTop(buildMenuBar());
        mStatus = new Label("Everything is copacetic");
        ToolBar toolBar = new ToolBar(mStatus);
        root.setBottom(toolBar);

        Scene scene = new Scene(root, 295, 415);

        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Graham Mix, CSCD 370 Lab 8, Winter 2020");
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
    
    private void onDrag(MouseEvent event){
        ImageView image = (ImageView)event.getSource();
        Dragboard dragboard = image.startDragAndDrop(TransferMode.COPY);
        dragboard.setDragView(image.getImage());
        ClipboardContent content = new ClipboardContent();
        content.putString(image.getUserData().toString());
        dragboard.setContent(content);
    }
    
    private void onDragOver(DragEvent event){
        ImageView image = (ImageView)event.getTarget();
        String data = image.getUserData().toString();
        if (!data.equals("BLANK")){
            return;
        }
        Dragboard board = event.getDragboard();
        String dataString = board.getString();
        if (dataString.equals("X") || dataString.equals("O")){
            event.acceptTransferModes(TransferMode.COPY);
        }
    }
    
    private void onDragDropped(DragEvent event){
        ImageView image = (ImageView)event.getTarget();
        Dragboard board = event.getDragboard();
        String dataString = board.getString();
        ImageView blank;
        if (dataString.equals("X")){
            blank = (ImageView)event.getTarget();
            blank.setImage(mXImage);
            blank.setUserData("X");
            event.setDropCompleted(true);
        }
        else if (dataString.equals("O")){
            blank = (ImageView)event.getTarget();
            blank.setImage(mOImage);
            blank.setUserData("O");
            event.setDropCompleted(true);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
