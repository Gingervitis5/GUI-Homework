/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmixhw2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Gingervitis
 */
public class GMixHW2 extends Application {
    
    Pendulum mBackground;
    ProgressBar mProgress;
    @Override
    public void start(Stage primaryStage) {
        
        mBackground = new Pendulum();
        
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(keyEvent -> onKey(keyEvent));
        
        root.setCenter(mBackground);
        
        ToolBar toolBar = new ToolBar();
        mProgress = new ProgressBar(0.0); 
        mProgress.progressProperty().bind(
                mBackground.locProperty().subtract(mBackground.minXProperty()).divide( mBackground.maxXProperty().subtract(mBackground.minXProperty()) ) );
        toolBar.getItems().add(mProgress);
        root.setBottom(toolBar);
        
        primaryStage.setTitle("Homework 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void onKey(KeyEvent key){
        switch(key.getCode()){
            case UP:
                mBackground.startTimer();
                break;
            case DOWN:
                mBackground.stopTimer();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
