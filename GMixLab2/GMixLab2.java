/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmixlab2;

import java.awt.image.BufferedImage;
import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
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
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import static javafx.embed.swing.SwingFXUtils.fromFXImage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import javax.imageio.ImageIO;

/**
 *
 * @author Gingervitis
 */
public class GMixLab2 extends Application {

    private Canvas mTempCanvas, mPermCanvas;
    private Label mStatus;
    private Point2D mPointFrom, mPointTo;
    private Window mStage;

    @Override
    public void start(Stage primaryStage) {

        mStage = primaryStage;
        BorderPane root = new BorderPane();

        //build menubar
        root.setTop(buildMenuBar());

        mStatus = new Label("Everything is copacetic");
        ToolBar toolBar = new ToolBar(this.mStatus);
        root.setBottom(toolBar);

        //build the canvas
        mTempCanvas = new Canvas(400, 400);
        mPermCanvas = new Canvas(400, 400);
        setCanvas();
        StackPane stackP = new StackPane(mTempCanvas, mPermCanvas);
        ScrollPane scrollP = new ScrollPane();
        scrollP.setContent(stackP);
        root.setCenter(scrollP);

        //add the mouse handlers
        mPermCanvas.setOnMousePressed(mouseEvent -> onMousePressed(mouseEvent));
        mPermCanvas.setOnMouseDragged(mouseEvent -> onMouseDragged(mouseEvent));
        mPermCanvas.setOnMouseReleased(mouseEvent -> onMouseReleased(mouseEvent));

        Scene scene = new Scene(root, 500, 500);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Graham Mix, CSCD 370 Lab 2, Winter 2020");
        alert.showAndWait();
    }

    private MenuBar buildMenuBar() {
        // build a menu bar
        MenuBar menuBar = new MenuBar();
        // File menu with a new item 
        Menu fileMenu = new Menu("_File");
        MenuItem newMenuItem = new MenuItem("_New");
        newMenuItem.setOnAction(actionEvent -> onNew());
        newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N,
                KeyCombination.CONTROL_DOWN));
        fileMenu.getItems().add(newMenuItem);
        //File menu with an open item
        MenuItem openMenuItem = new MenuItem("_Open");
        openMenuItem.setOnAction(actionEvent -> onOpen());
        openMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O,
                KeyCombination.CONTROL_DOWN));
        fileMenu.getItems().add(openMenuItem);
        //File menu with a save item
        MenuItem saveMenuItem = new MenuItem("_Save");
        saveMenuItem.setOnAction(actionEvent -> onSave(false));
        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S,
                KeyCombination.CONTROL_DOWN));
        fileMenu.getItems().add(saveMenuItem);
        //File menu with a save as item
        MenuItem saveAsMenuItem = new MenuItem("Save _As");
        saveAsMenuItem.setOnAction(actionEvent -> onSave(true));
        saveAsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.A,
                KeyCombination.CONTROL_DOWN));
        fileMenu.getItems().add(saveAsMenuItem);
        //Seperator item
        SeparatorMenuItem separator = new SeparatorMenuItem();
        fileMenu.getItems().add(separator);
        //File menu with an exit item
        MenuItem quitMenuItem = new MenuItem("E_xit");
        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q,
                KeyCombination.CONTROL_DOWN));
        quitMenuItem.setOnAction(actionEvent -> onExit());
        fileMenu.getItems().add(quitMenuItem);

        //Width menu
        Menu widthMenu = new Menu("_Width");
        RadioMenuItem onePixel = new RadioMenuItem("_1 Pixel");
        onePixel.setOnAction(actionEvent -> onPixelChoice(1.0));
        /*onePixel.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT1,
                KeyCombination.CONTROL_DOWN));*/
        widthMenu.getItems().add(onePixel);
        RadioMenuItem fourPixels = new RadioMenuItem("_4 Pixels");
        fourPixels.setOnAction(actionEvent -> onPixelChoice(4.0));
        /*fourPixels.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT4,
                KeyCombination.CONTROL_DOWN));*/
        widthMenu.getItems().add(fourPixels);
        RadioMenuItem eightPixels = new RadioMenuItem("_8 Pixels");
        eightPixels.setOnAction(actionEvent -> onPixelChoice(8.0));
        /*eightPixels.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT8,
                KeyCombination.CONTROL_DOWN));*/
        widthMenu.getItems().add(eightPixels);

        //Color menu
        Menu colorMenu = new Menu("_Color");
        RadioMenuItem black = new RadioMenuItem("_Black");
        black.setOnAction(actionEvent -> onColorChoice("Black"));
        colorMenu.getItems().add(black);
        RadioMenuItem red = new RadioMenuItem("_Red");
        red.setOnAction(actionEvent -> onColorChoice("Red"));
        colorMenu.getItems().add(red);
        RadioMenuItem green = new RadioMenuItem("_Green");
        green.setOnAction(actionEvent -> onColorChoice("Green"));
        colorMenu.getItems().add(green);
        RadioMenuItem blue = new RadioMenuItem("B_lue");
        blue.setOnAction(actionEvent -> onColorChoice("Blue"));
        colorMenu.getItems().add(blue);

        // Help menu with just an about item
        Menu helpMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);
        menuBar.getMenus().addAll(fileMenu, widthMenu, colorMenu, helpMenu);
        return menuBar;
    }

    public void setStatus(String content) {
        if (content == null || content.contentEquals("")) {
            mStatus.setText("Empty");
        } else {
            mStatus.setText(content);
        }
    }

    private void setCanvas() {
        GraphicsContext gc = mTempCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, mTempCanvas.getWidth(), mTempCanvas.getHeight());

    }

    private void onMousePressed(MouseEvent e) {
        mPointFrom = new Point2D(e.getX(), e.getY());
        mPointTo = new Point2D(e.getX(), e.getY());
        setStatus("Mouse was pressed");
    }

    private void onMouseDragged(MouseEvent e) {
        setCanvas();
        if (mTempCanvas.contains(e.getX(), e.getY())) {
            mPointTo = new Point2D(e.getX(), e.getY());
            DrawLine(mTempCanvas, Color.BLACK);
        } else {
            DrawLine(mTempCanvas, Color.BLACK);
        }
        setStatus("Mouse was dragged");
    }

    private void onMouseReleased(MouseEvent e) {
        if (mPermCanvas.contains(e.getX(), e.getY())) {
            mPointTo = new Point2D(e.getX(), e.getY());
            DrawLine(mPermCanvas, Color.RED);
        } else {
            DrawLine(mPermCanvas, Color.RED);
        }
        setStatus("Mouse was released");
    }

    private void DrawLine(Canvas canvas, Color color) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(color);
        gc.strokeLine(mPointFrom.getX(), mPointFrom.getY(), mPointTo.getX(), mPointTo.getY());
    }

    private void onNew() {
        setCanvas();
        setStatus("New was pressed");
        GraphicsContext gc = mPermCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mPermCanvas.getWidth(), mPermCanvas.getHeight());
    }

    private void onOpen() {
        setStatus("Open was pressed");
    }

    private void onSave(boolean condition) {
        if (condition == false) {
            setStatus("Save was pressed");
            /*FileChooser chooser = new FileChooser();
            chooser.setTitle("Scribble Image");
            chooser.getExtensionFilters().addAll(new ExtensionFilter("Portable Networks Graphic", "*.png"));

            File file = chooser.showSaveDialog(mStage);

            if (file != null) {
                try {
                    WritableImage savedImage = mPermCanvas.snapshot(null, null);
                    BufferedImage buff = fromFXImage(savedImage, null);
                    ImageIO.write(buff, "png", file);
                } catch (Exception e) {
                    System.out.print(e.getStackTrace());
                }
            }*/
        } else {
            setStatus("Save As was pressed");
        }

    }

    private void onExit() {
        Platform.exit();
    }

    private void onPixelChoice(double pixelSize) {
        /*GraphicsContext gc = mPermCanvas.getGraphicsContext2D();
        gc.setLineWidth(pixelSize);*/
        setStatus("Pixel size was chosen to be " + pixelSize);
    }

    private void onColorChoice(String color) {
        //GraphicsContext gc = mPermCanvas.getGraphicsContext2D();
        switch (color) {
            case "Black":
                //gc.setStroke(Color.BLACK);
                setStatus("Black was chosen");
                break;
            case "Red":
                //gc.setStroke(Color.RED);
                setStatus("Red was chosen");
                break;
            case "Green":
                //gc.setStroke(Color.GREEN);
                setStatus("Green was chosen");
                break;
            case "Blue":
                //gc.setStroke(Color.BLUE);
                setStatus("Blue was chosen");
                break;
            default:
                //gc.setStroke(Color.BLACK);
                break;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
