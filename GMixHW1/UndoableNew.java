/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmixhw1;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javax.swing.undo.AbstractUndoableEdit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gingervitis
 */
public class UndoableNew extends AbstractUndoableEdit{
    
    Canvas mSceneCanvas;
    Canvas mBackup;
    
    public UndoableNew(Canvas newCanvas){
        mSceneCanvas = newCanvas;
        WritableImage imageBackup = newCanvas.snapshot(null, null);
        mBackup = new Canvas(mSceneCanvas.getWidth(), mSceneCanvas.getHeight());
        GraphicsContext gc = mBackup.getGraphicsContext2D();
        gc.drawImage(imageBackup, 0, 0);
    }
    
    @Override
    public void undo(){
        WritableImage undo = mBackup.snapshot(null, null);
        GraphicsContext gc = mSceneCanvas.getGraphicsContext2D();
        gc.drawImage(undo, 0, 0);        
    }
    
    @Override
    public String getPresentationName(){
        return "New";
    }
    
}
