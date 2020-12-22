/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ashman;

import java.io.Serializable;
import java.nio.file.Paths;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

/**
 *
 * @author Gingervitis
 */
public class Ashman extends Entity implements Serializable{
    
    
    public Ashman(Direction d, int cx, int cy){
        super(d,cx,cy);
    }


    @Override
    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.save();
        gc.scale(canvas.getWidth()/20, canvas.getHeight()/20);
        gc.setFill(Color.RED);     
        if (!isNextToWall()){
            gc.fillOval(mCellX+0.125, mCellY+0.125, 0.75, 0.75);
        }
        collision();
        gc.restore();
    }
    
    public void setDirection(Direction d){
        super.mDirection = d;
    }
    
    public void setCoordinates(int x, int y){
        this.mCellX = x;
        this.mCellY = y;
    }
    
    private void collision(){
        for (Ghost g : Game.getGhosts()){
            if (this.mCellX == g.mCellX && this.mCellY == g.mCellY || isInFront()){
                Game.getTimer().stop();
                AudioClip death = new AudioClip(getClass().getResource("death.wav").toString());
                death.play();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setContentText("You have died");
                alert.setHeaderText("Uh Oh!");
                alert.show();
            }
        }
        for (Ghost g : Game.getAddGhosts()){
            if (this.mCellX == g.mCellX && this.mCellY == g.mCellY || isInFront()){
                Game.getTimer().stop();
                AudioClip death = new AudioClip(getClass().getResource("death.wav").toString());
                death.play();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setContentText("You have died");
                alert.setHeaderText("Uh Oh!");
                alert.show();
            }
        }
        if (Game.getMaze()[mCellY][mCellX] == 1) {
            Game.updateCakes(-1);
            Game.updateScore(1);
            Game.getMaze()[mCellY][mCellX] = 2;
        }
        switch(mDirection){
            case RIGHT:
                ++mCellX;
                break;
            case LEFT:
                --mCellX;
                break;
            case DOWN:
                ++mCellY;
                break;
            case UP:
                --mCellY;
                break;
        }
    }
    
    public boolean isNextToWall(){
        int[][] maze = Game.getMaze();
        if (mDirection != Direction.NONE){
            if (mDirection == Direction.RIGHT){
                if (maze[mCellY][mCellX+1] == 0){
                    mDirection = Direction.NONE;
                    return true;
                }
            }
            else if (mDirection == Direction.LEFT){
                if (maze[mCellY][mCellX-1] == 0){
                    mDirection = Direction.NONE;
                    return true;
                }
            }
            else if (mDirection == Direction.UP){
                if (maze[mCellY-1][mCellX] == 0){
                    mDirection = Direction.NONE;
                    return true;
                }
            }
            else if (mDirection == Direction.DOWN){
                if (maze[mCellY+1][mCellX] == 0){
                    mDirection = Direction.NONE;
                    return true;
                }  
            }
        }
        return false;
    }
    
    private boolean isInFront(){
        for (Ghost g : Game.getGhosts()){
            switch(mDirection){
                case RIGHT:
                    if (mCellX+1 == g.mCellX && mCellY == g.mCellY){
                        return true;
                    }
                case LEFT:
                    if (mCellX-1 == g.mCellX && mCellY == g.mCellY){
                        return true;
                    }
                case UP:
                    if (mCellY-1 == g.mCellY && mCellX == g.mCellX){
                        return true;
                    }
                case DOWN:
                    if (mCellY+1 == g.mCellY && mCellX == g.mCellX){
                        return true;
                    }
            }
        }
        for (Ghost g : Game.getAddGhosts()){
            switch(mDirection){
                case RIGHT:
                    if (mCellX+1 == g.mCellX && mCellY == g.mCellY){
                        return true;
                    }
                case LEFT:
                    if (mCellX-1 == g.mCellX && mCellY == g.mCellY){
                        return true;
                    }
                case UP:
                    if (mCellY-1 == g.mCellY && mCellX == g.mCellX){
                        return true;
                    }
                case DOWN:
                    if (mCellY+1 == g.mCellY && mCellX == g.mCellX){
                        return true;
                    }
            }
        }
        return false;
    }
}
