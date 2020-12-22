/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmixlab4;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.collections.ObservableList;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

/**
 *
 * @author Gingervitis
 */
public class GMixLab4 extends Application {

    private Canvas mTempCanvas, mPermCanvas;
    private Label mStatus;
    private Point2D mPointFrom, mPointTo;
    private Window mStage;
    private Color mColor;
    private double mWidth;
    private int mColorCycle = 2, mWidthCycle = 2;
    private ToggleGroup mColorGroup, mWidthGroup;
    private ToolbarPos mPosition = ToolbarPos.LEFT;
    private BorderPane mRoot;
    private VBox mVbox;

    @Override
    public void start(Stage primaryStage) {

        mStage = primaryStage;
        mRoot = new BorderPane();

        mVbox = new VBox(0, buildMenuBar());

        //build menubar
        mRoot.setTop(mVbox);
        mRoot.setLeft(buildToolBar(true));

        mStatus = new Label("Everything is copacetic");
        ToolBar toolBar = new ToolBar(this.mStatus);
        mRoot.setBottom(toolBar);

        //build the canvas
        mTempCanvas = new Canvas(400, 400);
        mPermCanvas = new Canvas(400, 400);
        setCanvas();
        StackPane stackP = new StackPane(mTempCanvas, mPermCanvas);
        ScrollPane scrollP = new ScrollPane();
        scrollP.setContent(stackP);
        mRoot.setCenter(scrollP);

        //add the mouse handlers
        mPermCanvas.setOnMousePressed(mouseEvent -> onMousePressed(mouseEvent));
        mPermCanvas.setOnMouseDragged(mouseEvent -> onMouseDragged(mouseEvent));
        mPermCanvas.setOnMouseReleased(mouseEvent -> onMouseReleased(mouseEvent));

        Scene scene = new Scene(mRoot, 500, 500);

        primaryStage.setTitle("Lab 4");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Graham Mix, CSCD 370 Lab 4, Winter 2020");
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
        mWidthGroup = new ToggleGroup();
        Menu widthMenu = new Menu("_Width");
        RadioMenuItem onePixel = new RadioMenuItem("_1 Pixel");
        onePixel.setToggleGroup(mWidthGroup);
        onePixel.setSelected(true);
        mColor = Color.BLACK;
        onePixel.setOnAction(actionEvent -> onPixelChoice(1));
        widthMenu.getItems().add(onePixel);

        RadioMenuItem fourPixels = new RadioMenuItem("_4 Pixels");
        fourPixels.setToggleGroup(mWidthGroup);
        fourPixels.setOnAction(actionEvent -> onPixelChoice(2));
        widthMenu.getItems().add(fourPixels);

        RadioMenuItem eightPixels = new RadioMenuItem("_8 Pixels");
        eightPixels.setToggleGroup(mWidthGroup);
        eightPixels.setOnAction(actionEvent -> onPixelChoice(3));
        widthMenu.getItems().add(eightPixels);

        widthMenu.setOnShowing(event -> onWidthShowing(true));

        //Color menu
        mColorGroup = new ToggleGroup();
        Menu colorMenu = new Menu("_Color");
        RadioMenuItem black = new RadioMenuItem("_Black");
        black.setToggleGroup(mColorGroup);
        black.setSelected(true);
        mWidth = 1.0;
        black.setOnAction(actionEvent -> onColorChoice(1));
        colorMenu.getItems().add(black);

        RadioMenuItem red = new RadioMenuItem("_Red");
        red.setToggleGroup(mColorGroup);
        red.setOnAction(actionEvent -> onColorChoice(2));
        colorMenu.getItems().add(red);

        RadioMenuItem green = new RadioMenuItem("_Green");
        green.setToggleGroup(mColorGroup);
        green.setOnAction(actionEvent -> onColorChoice(3));
        colorMenu.getItems().add(green);

        RadioMenuItem blue = new RadioMenuItem("B_lue");
        blue.setToggleGroup(mColorGroup);
        blue.setOnAction(actionEvent -> onColorChoice(4));
        colorMenu.getItems().add(blue);

        // Help menu with just an about item
        Menu helpMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);
        menuBar.getMenus().addAll(fileMenu, widthMenu, colorMenu, helpMenu);

        colorMenu.setOnShowing(event -> onColorShowing(true));

        return menuBar;
    }

    private ToolBar buildToolBar(boolean orient) {
        ToolBar toolBar = new ToolBar();
        if (orient){
            toolBar.setOrientation(Orientation.VERTICAL);
        }
        else {
            toolBar.setOrientation(Orientation.HORIZONTAL);
        }
        Separator separator = new Separator(Orientation.HORIZONTAL);

        Image newImage = new Image("ButtonImages/Color.png");
        ImageView imageView = new ImageView(newImage);
        Button colorButton = new Button();
        colorButton.setGraphic(imageView);
        colorButton.setTooltip(new Tooltip("Cycle to the next color"));
        toolBar.getItems().addAll(colorButton, separator);
        colorButton.setOnAction(actionEvent -> onColorChoice(mColorCycle));
        separator = new Separator(Orientation.HORIZONTAL);

        newImage = new Image("ButtonImages/Width.png");
        imageView = new ImageView(newImage);
        Button widthButton = new Button();
        widthButton.setGraphic(imageView);
        widthButton.setTooltip(new Tooltip("Cycle to the next line width"));
        toolBar.getItems().addAll(widthButton, separator);
        widthButton.setOnAction(actionEvent -> onPixelChoice(mWidthCycle));
        separator = new Separator(Orientation.HORIZONTAL);

        newImage = new Image("ButtonImages/Move.png");
        imageView = new ImageView(newImage);
        Button moveButton = new Button();
        moveButton.setGraphic(imageView);
        moveButton.setTooltip(new Tooltip("Move the toolbar"));
        toolBar.getItems().addAll(moveButton, separator);
        moveButton.setOnAction(actionEvent -> onToolbarMove());
        separator = new Separator(Orientation.HORIZONTAL);

        newImage = new Image("ButtonImages/New.png");
        imageView = new ImageView(newImage);
        Button newButton = new Button();
        newButton.setGraphic(imageView);
        newButton.setTooltip(new Tooltip("Create a new canvas"));
        toolBar.getItems().addAll(newButton, separator);
        newButton.setOnAction(actionEvent -> onNew());
        separator = new Separator(Orientation.HORIZONTAL);

        newImage = new Image("ButtonImages/Open.png");
        imageView = new ImageView(newImage);
        Button openButton = new Button();
        openButton.setGraphic(imageView);
        openButton.setTooltip(new Tooltip("Open a file"));
        toolBar.getItems().addAll(openButton, separator);
        openButton.setOnAction(actionEvent -> onOpen());

        newImage = new Image("ButtonImages/Save.png");
        imageView = new ImageView(newImage);
        Button saveButton = new Button();
        saveButton.setGraphic(imageView);
        saveButton.setTooltip(new Tooltip("Save the current canvas"));
        toolBar.getItems().add(saveButton);
        saveButton.setOnAction(actionEvent -> onSave(false));

        return toolBar;
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
            DrawLine(mTempCanvas, mColor);
        } else {
            DrawLine(mTempCanvas, mColor);
        }
        setStatus("Mouse was dragged");
    }

    private void onMouseReleased(MouseEvent e) {
        if (mPermCanvas.contains(e.getX(), e.getY())) {
            mPointTo = new Point2D(e.getX(), e.getY());
            DrawLine(mPermCanvas, mColor);
        } else {
            DrawLine(mPermCanvas, mColor);
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

    private void onPixelChoice(int num) {
        GraphicsContext gc = mPermCanvas.getGraphicsContext2D();
        switch (num) {
            case 1:
                gc.setLineWidth(1.0);
                mWidth = 1.0;
                break;
            case 2:
                gc.setLineWidth(4.0);
                mWidth = 4.0;
                break;
            case 3:
                gc.setLineWidth(8.0);
                mWidth = 8.0;
                break;
        }
        setStatus("Pixel size was chosen to be " + mWidth);
        if (mWidthCycle >= 3) {
            mWidthCycle = 1;
        } else {
            mWidthCycle = num + 1;
        }

    }

    private void onColorChoice(int num) {
        GraphicsContext gc = mPermCanvas.getGraphicsContext2D();
        switch (num) {
            case 1:
                gc.setStroke(Color.BLACK);
                setStatus("Black was chosen");
                mColor = Color.BLACK;
                break;
            case 2:
                gc.setStroke(Color.RED);
                setStatus("Red was chosen");
                mColor = Color.RED;
                break;
            case 3:
                gc.setStroke(Color.GREEN);
                setStatus("Green was chosen");
                mColor = Color.GREEN;
                break;
            case 4:
                gc.setStroke(Color.BLUE);
                setStatus("Blue was chosen");
                mColor = Color.BLUE;
                break;
            default:
                gc.setStroke(Color.BLACK);
                mColor = Color.BLACK;
                break;

        }
        if (mColorCycle >= 4) {
            mColorCycle = 1;
        } else {
            mColorCycle = num + 1;
        }
    }

    private void onToolbarMove() {
        switch (mPosition) {
            case LEFT:
                mPosition = ToolbarPos.TOP;
                mVbox = new VBox(0, buildMenuBar(), buildToolBar(false));
                mRoot.setTop(mVbox);
                mRoot.setLeft(null);
                break;
            case TOP:
                mPosition = ToolbarPos.RIGHT;
                mVbox = new VBox(0, buildMenuBar());
                mRoot.setRight(buildToolBar(true));
                mRoot.setTop(mVbox);
                break;
            case RIGHT:
                mPosition = ToolbarPos.LEFT;
                mRoot.setLeft(buildToolBar(true));
                mRoot.setRight(null);
                break;
        }
        setStatus("Move button was pressed");
    }

    private void onColorShowing(boolean arg) {
        int i = -1;
        ObservableList<Toggle> buttonList = mColorGroup.getToggles();
        if (arg) {
            if (mColor.equals(Color.BLACK)) {
                i = 0;
            } else if (mColor.equals(Color.RED)) {
                i = 1;
            } else if (mColor.equals(Color.GREEN)) {
                i = 2;
            } else if (mColor.equals(Color.BLUE)) {
                i = 3;
            } else {
                i = 0;
            }
        }
        RadioMenuItem showing = (RadioMenuItem) buttonList.get(i);
        mColorGroup.selectToggle(showing);
    }

    private void onWidthShowing(boolean arg) {
        int i = -1;
        ObservableList<Toggle> widthList = mWidthGroup.getToggles();
        if (arg) {
            if (mWidth == 1.0) {
                i = 0;
            } else if (mWidth == 4.0) {
                i = 1;
            } else if (mWidth == 8.0) {
                i = 2;
            } else {
                i = 0;
            }
        }
        RadioMenuItem showing = (RadioMenuItem) widthList.get(i);
        mWidthGroup.selectToggle(showing);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
