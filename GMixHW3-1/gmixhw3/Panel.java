/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmixhw3;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

/**
 *
 * @author Gingervitis
 */
public class Panel extends Region{
    
    private Label mLabel;
    private String name;
    private  Color mColor;
    private  Rectangle mBackground;
    private final double DIMX = 80, DIMY = 20;
    private final double ASPECT = DIMX/DIMY;
    private  ContextMenu mContext = new ContextMenu();
    private  Clipboard mClipboard = Clipboard.getSystemClipboard();
    private  ClipboardContent mContent;
    private  static DataFormat df = new DataFormat("gmixhw3/clipboardData");;
    
    public Panel(Color c, String n) throws IOException{
        mBackground = new Rectangle(DIMX, DIMY, c);
        mBackground.widthProperty().bind(this.widthProperty());
        mBackground.heightProperty().bind(this.heightProperty());
        createLabel();
        super.getChildren().add(mBackground);
        super.getChildren().add(mLabel);
        mColor = c;
        name = n;
        createContext();
        this.setOnMousePressed(event -> showContext(event));
    }
    
    private void copy(){
        MyData data = new MyData(mColor.getRed(), mColor.getGreen(), mColor.getBlue(), mLabel.getText());
        mContent = new ClipboardContent();
        mContent.putString(mLabel.getText());
        mContent.put(df, data);
        mContent.putString("custom content here:\n" + mLabel.getText() + "\nRed:" + mColor.getRed() + 
                                                                         "\nGreen:" + mColor.getGreen() +
                                                                         "\nBlue:" + mColor.getBlue());
        mClipboard.setContent(mContent);
    }
    
    private void paste(){
        if(mClipboard.hasContent(df)){
            MyData data = (MyData)mClipboard.getContent(df);
            Color c = new Color(data.r, data.g, data.b, 1.0);
            mBackground.setFill(c);
            mColor = c;
            mLabel.setText(data.text);
        }
        else if (mClipboard.hasString()){
            if (!mClipboard.getString().equals("")){
                String text = mClipboard.getString().trim();
                mLabel.setText(text);
            }
        }
        else {
            GMixHW3.setStatus("Paste Failure");
        }
        this.layoutChildren();
    }
    
    
    private void createLabel(){
        mLabel = new Label("Place Text Here");
        mLabel.setFont(Font.font("Times New Roman", 20));
        mLabel.setPrefSize(300, 300);
    }
    
    private void createContext(){
        MenuItem changeString = new MenuItem("Change String");
        changeString.setOnAction(event -> {
            try {
                onStringChange();
            } catch (IOException ex) {
                Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        mContext.getItems().add(changeString);
        MenuItem changeColor = new MenuItem("Change Color");
        changeColor.setOnAction(event -> onColorChange());
        mContext.getItems().add(changeColor);
        MenuItem copySettings = new MenuItem("Copy Settings");
        copySettings.setOnAction(event -> copy());
        mContext.getItems().add(copySettings);
        MenuItem pasteSettings = new MenuItem("Paste Settings");
        pasteSettings.setOnAction(event -> paste());
        mContext.getItems().add(pasteSettings);
    }
    
    @Override
    protected void layoutChildren(){
        super.layoutChildren();       
        double labelH = mLabel.getHeight();
        double labelW = mLabel.getWidth();
        mLabel.relocate((mBackground.getWidth()/2.0)-(labelW/4.0), (mBackground.getHeight()/2.0)-(labelH/2.0));
        
    }

    private void onColorChange() {
        Random rand = new Random();
        float r = rand.nextFloat() % 255;
        float g = rand.nextFloat() % 255;
        float b = rand.nextFloat() % 255;
        Color c = new Color(r,g,b, 1.0);
        mColor = c;
        mBackground.setFill(c);
    }

    private void showContext(MouseEvent e) {
        if (e.isSecondaryButtonDown()){
            mContext.show(mBackground, e.getScreenX(), e.getScreenY());
        }
    }

    private void onStringChange() throws IOException {
        TextInputDialog tid = new TextInputDialog("Text Input");
        tid.setTitle("Text input dialog");
        tid.setHeaderText("Please enter some text");
        tid.setContentText("Enter Something Here");
        
        Optional<String> result = tid.showAndWait();
        if (result.isPresent()){
            mLabel.setText(result.get());
        }
    }
}

class MyData implements Serializable {
        double r, g, b;
        String text;
        
        public MyData(double r, double g, double b, String text){
            this.r = r;
            this.g = g;
            this.b = b;
            this.text = text;
        }
        
    }
