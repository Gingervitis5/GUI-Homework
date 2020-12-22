/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ashman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

/**
 *
 * @author Gingervitis
 */
public class Game extends Region implements Serializable{
    
    private Canvas mCanvas;
    private File mazeFile;
    private static int[][] mMaze = new int[20][20];
    private static int mNumCakes, mScore;
    private int mMaxCakes;
    private double mTransX = 0, mTransY = 0;
    private final double mCellX = 30, mCellY = 30;
    private double mCountX = 0;
    public static AnimationTimer mTimer;
    private GraphicsContext mGc;
    public static Ashman ashman;
    private static ArrayList<Ghost> ghostList = new ArrayList<Ghost>(), addGhosts = new ArrayList<Ghost>();
    public GhostSettings mSettings;
    private int mLevel = 1;
    private long mPreviousAshTime, mPreviousGhostTime;
    private final AudioClip eatCake;
    private final Ghost greeny, pinky, orangey, yellowy;
            
    public Game(){
        mCanvas = new Canvas(mCellX*20,mCellY*20);
        mazeFile = new File("ashman/Maze1.txt");
        mGc = mCanvas.getGraphicsContext2D();
        this.setPrefSize(mCanvas.getWidth()*1000, mCanvas.getHeight()*1000);
        this.getChildren().add(mCanvas);
        mTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onAshmanTimer(now);
                onGhostTimer(now);
            }
        };
        setMaze();
        ashman = new Ashman(Direction.NONE, 1, 1);
        mSettings = GhostSettings.createSettings(2, 1);
        eatCake = new AudioClip(getClass().getResource("eatcake.wav").toString());
        greeny = new Ghost(Ghost.getRandomDirection(), 17, 7, Color.MEDIUMSPRINGGREEN);
        pinky = new Ghost(Ghost.getRandomDirection(), 17, 7, Color.MAGENTA);
        orangey = new Ghost(Ghost.getRandomDirection(), 17, 7, Color.ORANGE);
        yellowy = new Ghost(Ghost.getRandomDirection(), 17, 7, Color.GOLD);
        ghostList.add(greeny);
        ghostList.add(pinky);
    }
    
    public int getLevel(){
        return mLevel;
    }
    
    public double getCanvasWidth(){
        return mCanvas.getWidth();
    }
    
    public double getCanvasHeight(){
        return mCanvas.getHeight();
    }
    
    public static int[][] getMaze(){
        return mMaze;
    }
    
    public static void updateCakes(int num){
        mNumCakes += num;
    }
    
    public static void updateScore(int num){
        mScore += 1;
    }
    
    public static int getCakes(){
        return mNumCakes;
    }
    
    public static int getScore(){
        return mScore;
    }
    
    public static AnimationTimer getTimer(){
        return mTimer;
    }
    
    public static ArrayList<Ghost> getGhosts(){
        return ghostList;
    }
    
    public static ArrayList<Ghost> getAddGhosts(){
        return addGhosts;
    }
    
    public void createWall(){
        mGc.translate(mTransX, mTransY);
        mGc.setFill(Color.BLACK);
        mGc.fillRect(0, 0, mCellX+2, mCellY+2);
        translate();
    }
    
    public void createBlank(){
        strokeBox();
        mGc.setFill(Color.BLUE);
        mGc.fillRect(0, 0, mCellX, mCellY);

        translate();
    }
    
    public void createCake() {
        strokeBox();

        mGc.setFill(Color.BLUE);
        mGc.fillRect(0, 0, mCellX, mCellY);

        mGc.setFill(Color.WHITE);
        double topX = mCellX / 4, topY = mCellY / 4;
        mGc.fillOval(topX, topY, 15, 15);
        translate();
    }
    
    @Override
    protected void layoutChildren(){
        super.layoutChildren();
        double availableHeight = this.getHeight();
        double availableWidth = this.getWidth();
        mCanvas.setHeight(availableHeight);
        mCanvas.setWidth(availableWidth);
        draw(mMaze);
    }
    
    public void draw(int[][] array){
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array[i].length; j++){
                mGc.save();
                mGc.scale((mCanvas.getHeight()/(mCellX*20)), (mCanvas.getHeight()/(mCellY*20)));
                switch (array[i][j]) {
                    case 2:
                        this.createBlank();
                        break;
                    case 1:
                        this.createCake();
                        break;
                    case 0:
                        this.createWall();
                        break;
                }
                mGc.restore();
            }
        }
        mTransX = 0; mTransY = 0;
    }
    
    private void setMaze(){
        try{
            Scanner ms = new Scanner(mazeFile);
            for (int i = 0; i < 19; i++){
                for (int j = 0; j < 20; j++){
                    if (ms.hasNext()){
                        mMaze[i][j] = ms.nextInt();
                        if (mMaze[i][j] == 1){
                            mNumCakes += 1;
                        }
                    }
                }
                ms.nextLine();
            }
            mMaxCakes = mNumCakes;
        } catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }   
    }
    
    private void strokeBox(){
        mGc.translate(mTransX+1, mTransY+1);
        mGc.setFill(Color.BLACK);
        mGc.setLineWidth(1.5);
        mGc.strokeLine(0, 0, 0, mCellY);
        mGc.strokeLine(0, 0, mCellX, 0);
        mGc.strokeLine(mCellX, 0, mCellX, mCellY);
        mGc.strokeLine(0, mCellY, mCellX, mCellY);
    }
    
    private void translate(){
        if (mCountX < 19){
            mTransX += 30;
            mCountX++;
        }
        else {
            mTransX = 0;
            mTransY += 30;
            mCountX = 0;
        }
    }
    
    private void onGhostTimer(long now){
        now = System.currentTimeMillis();
        long elapsed = now-mPreviousGhostTime;
        switch(mSettings.getSpeed()){
            case 1:
                if (elapsed > 500){
                    for (Ghost g : ghostList){
                            g.draw(mCanvas);
                    }
                    for (Ghost g : addGhosts){
                            g.draw(mCanvas);
                    }
                    mPreviousGhostTime = now;
                }
                break;
            case 2:
                if (elapsed > 250){
                    for (Ghost g : ghostList) {
                        g.draw(mCanvas);
                    }
                    for (Ghost g : addGhosts){
                            g.draw(mCanvas);
                    }
                    mPreviousGhostTime = now;
                }
        }
    }
    
    private void onAshmanTimer(long now){  
        now = System.currentTimeMillis();
        long elapsed = now-mPreviousAshTime;
        if (elapsed > 250){
            draw(mMaze);
            ashman.draw(mCanvas);
            if (ashman.mDirection != Direction.NONE && mMaze[ashman.mCellY][ashman.mCellX] == 1){
                eatCake.play();
            }
            Main.update();
            mPreviousAshTime = now;
        }
        if (mNumCakes == 0 && mLevel == 1){
            winLevel1();
        }
        else if (mNumCakes == 0 && mLevel == 2){
            winLevel2();
        }
    }
    
    private void winLevel1(){
        mTimer.stop();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Winner!");
        alert.setHeaderText("Congratulations, you won!");
        alert.setContentText("Proceeding to next level. Please select Game->Go to start");
        ButtonType okay = new ButtonType("Okay");
        alert.getButtonTypes().setAll(okay);
        alert.show();
        mazeFile = new File("ashman/Maze2.txt");
        ashman.mDirection = Direction.NONE;
        mLevel = 2;
        mSettings.setNumGhosts(4);
        mSettings.setSpeed(2);
        reflectPreferences(false);
        setMaze();
        reset();
        drawEntities();
        Main.update();
    }
    
    private void winLevel2(){
        mTimer.stop();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Winner!");
        alert.setHeaderText("Congratulations, you beat the game!");
        ButtonType okay = new ButtonType("Okay", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okay);
        alert.show();
    }
    
    public void cheat(){
        for (int i = 0; i < mMaze.length; i++){
            for (int j = 0; j < mMaze[i].length; j++){
                if (mMaze[i][j] == 1){
                    mMaze[i][j] = 2;
                }
            }
        }
        mMaze[1][2] = 1;
        mScore = mMaxCakes-1;
        mNumCakes = 1;
        draw(mMaze);
        resetCoordinates();
        drawEntities();
    }
    
    public void reflectPreferences(boolean readNeeded){
        if (readNeeded){
            mSettings.readPreferences(getClass());
        }
        if (mSettings.getNumGhosts() < ghostList.size()){
            while (ghostList.size() > mSettings.getNumGhosts()){
                switch (ghostList.size()){
                    case 4:
                        ghostList.remove(yellowy);
                        break;
                    case 3:
                        ghostList.remove(orangey);
                        break;
                    case 2:
                        ghostList.remove(pinky);
                        break;
                    case 1:
                        ghostList.remove(greeny);
                }
            }
        }
        else if (mSettings.getNumGhosts() > ghostList.size()){
            while (ghostList.size() < mSettings.getNumGhosts()){
                switch (ghostList.size()){
                    case 3:
                        ghostList.add(yellowy);
                        break;
                    case 2:
                        ghostList.add(orangey);
                        break;
                    case 1:
                        ghostList.add(pinky);
                }
            }
        }
        if (mSettings.getNumAddGhosts() < addGhosts.size()){
            while (mSettings.getNumAddGhosts() < addGhosts.size()){
                addGhosts.remove(addGhosts.size()-1);
            }
        }
        else if (mSettings.getNumAddGhosts() > addGhosts.size()){
            while (mSettings.getNumAddGhosts() > addGhosts.size()){
                addGhosts.add(new Ghost(Ghost.getRandomDirection(), 17, 7, Color.PURPLE));
            }
        }
    }
    
    public void reset(){
        mScore = 0; 
        mNumCakes = mMaxCakes;
        resetCoordinates();
        for (int i = 0; i < mMaze.length; i++){
            for (int j = 0; j < mMaze[i].length; j++){
                if (mMaze[i][j] == 2){
                    mMaze[i][j] = 1;
                }
            }
        }
        draw(mMaze);
        drawEntities();
        Main.onPause();
    }
    
    private void resetCoordinates(){
         ashman.setCoordinates(1, 1);
        for (int i = 0; i < ghostList.size(); i++){
            ghostList.get(i).setCoordinates(17, 7);
        }
    }
    
    private void drawEntities(){
        ashman.draw(mCanvas);
        for (Ghost g : ghostList){
            g.draw(mCanvas);
        }
        if (addGhosts.size() > 0){
            for (Ghost g : addGhosts){
                g.draw(mCanvas);
            }
        }
    }
}
