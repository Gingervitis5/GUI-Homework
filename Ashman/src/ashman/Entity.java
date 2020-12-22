/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ashman;

import java.io.Serializable;
import javafx.scene.canvas.Canvas;

/**
 *
 * @author Gingervitis
 */
public abstract class Entity implements Serializable{
    
    protected Direction mDirection;
    protected int mCellX, mCellY;
    protected int mSubstep;
    protected int mSpeed;
    
    public Entity(Direction d, int cx, int cy){
       mDirection = d;
       mCellX = cx;
       mCellY = cy;
    }
    
    public abstract void draw(Canvas canvas);
}
