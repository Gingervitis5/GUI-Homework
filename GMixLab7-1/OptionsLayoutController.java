/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmixlab7;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Gingervitis
 */
public class OptionsLayoutController extends Dialog<Void>{

    @FXML
    GridPane mRoot;
    @FXML
    RadioButton mDateTime;
    @FXML
    RadioButton mShowString;
    @FXML
    CheckBox mItalics;
    @FXML
    CheckBox mBold;
    @FXML
    TextField mTextField;
    @FXML
    TextField mFontSize;
    
    ButtonType mOkButt;
    ApplicationSettings mSettings;
    
    public OptionsLayoutController() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OptionsLayout.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        this.getDialogPane().setContent(root);

        mOkButt = new ButtonType("Apply", ButtonData.APPLY);
        
        ButtonType cancelButt = new ButtonType("Close", ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().add(mOkButt);
        this.getDialogPane().getButtonTypes().add(cancelButt);
        
        Button butt = (Button)this.getDialogPane().lookupButton(mOkButt);
        butt.addEventFilter(ActionEvent.ACTION,
                            event -> onOK(event));
        initialize();
    }  
    
    @FXML
    public void onDateTime(){
        mTextField.setDisable(true);
    }

    @FXML
    public void onShowString(){
        mTextField.setDisable(false);
    }
    
    public void onOK(ActionEvent event){
        try{
            int textSize = Integer.parseInt(mFontSize.getText());
            if (textSize < 8 || textSize > 40){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Enter a valid font size between 8 and 40");
                alert.showAndWait();
                mFontSize.requestFocus();
                event.consume();
            }
            mSettings.setFontSize(textSize);
            mSettings.setItalicsOn(mItalics.isSelected());
            mSettings.setBoldOn(mBold.isSelected());
            mSettings.setDateDisplayed(mDateTime.isSelected());
            if (mShowString.isSelected()){
                mSettings.setString(mTextField.getText());
            }
            mSettings.storePreferences(getClass());
            event.consume();
        } catch(NullPointerException e){
            System.out.print(e.getMessage());
        }
    }
    
    public void initialize(){
        mSettings = ApplicationSettings.createSettings(false, false, false, 20, "");
        mSettings.readPreferences(getClass());
    }
    
}
//xmlns="http://javafx.com/javafx" xmlns:fx="http://javafx.com/fxml"