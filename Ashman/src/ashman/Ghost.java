/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ashman;

import java.io.Serializable;
import java.util.Random;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

/**
 *
 * @author Gingervitis
 */
public class Ghost extends Entity implements Serializable{
    
    private Color mColor;
    
    public Ghost(Direction d, int cx, int cy, Color c) {
        super(d, cx, cy);
        mColor = c;
    }
    

    @Override
    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.save();
        gc.scale(canvas.getWidth()/20, canvas.getHeight()/20);
        gc.setFill(mColor);     
        if (!isNextToWall()){
            switch (mDirection){
                case RIGHT:
                    gc.fillOval(mCellX+0.125, mCellY+0.125, 0.75, 0.75);
                    mCellX+=1;
                    break;
                case LEFT:
                    gc.fillOval(mCellX+0.125, mCellY+0.125, 0.75, 0.75);
                    mCellX-=1;
                    break;
                case UP:
                    gc.fillOval(mCellX+0.125, mCellY+0.125, 0.75, 0.75);
                    mCellY-=1;
                    break;
                case DOWN:
                    gc.fillOval(mCellX+0.125, mCellY+0.125, 0.75, 0.75);
                    mCellY+=1;
                    break;
            }
        }
        gc.restore();
    }
    
    public void setDirection(Direction d){
        super.mDirection = d;
    }
    
    public void setCoordinates(int x, int y){
        this.mCellX = x;
        this.mCellY = y;
    }
    
    
    public boolean isNextToWall(){
        int[][] maze = Game.getMaze();
        if (mDirection == Direction.RIGHT){
            if (maze[mCellY][mCellX+1] == 0){
                mDirection = getRandomDirection();
                return true;
            }
        }
        else if (mDirection == Direction.LEFT){
            if (maze[mCellY][mCellX-1] == 0){
                mDirection = getRandomDirection();
                return true;
            }
        }
        else if (mDirection == Direction.UP){
            if (maze[mCellY-1][mCellX] == 0){
                mDirection = getRandomDirection();
                return true;
            }
        }
        else if (mDirection == Direction.DOWN){
            if (maze[mCellY+1][mCellX] == 0){
                mDirection = getRandomDirection();
                return true;
            }  
        }
        return false;
    }
    
    public static Direction getRandomDirection(){
        double random = Math.random();
        if (random >= 0 && random <= 0.1){
            return Direction.UP;
        }
        else if (random >= 0.2 && random <= 0.4){
            return Direction.DOWN;
        }
        else if (random >= 0.5 && random <= 0.7){ 
            return Direction.LEFT;
        }
        else {
            return Direction.RIGHT;
        }
    }
}
