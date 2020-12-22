/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmixlab3;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 *
 * @author Gingervitis
 */
public class SevenSegment extends Region{
    
    private Canvas mCanvas = new Canvas(6*DIMX, 6*DIMY);
    private int mValueOn;
    private boolean mValues[][] = {{true, true, false, true, true, true, true},
                                    {false, true, false, false, false, true, false}, 
                                    {true, true, true, false, true, false, true},
                                    {true, true, true, false, false, true, true},
                                    {false, true, true, true, false, true, false},
                                    {true, false, true, true, false, true, true},
                                    {true, false, true, true, true, true, true}, 
                                    {true, true, false, false, false, true, false}, 
                                    {true, true, true, true, true, true, true},
                                    {true, true, true, true, false, true, true}, 
                                    {false, false, false, false, false, false, false}};
    private boolean[] mSegmentArr;
    private static final double DIMX = 20, DIMY = 34;
    private static final double ASPECT = DIMX/DIMY;
    private static final Color ON = Color.RED, OFF = Color.color(1, 0, 0, 0.1);
    private static final double[] XCOOR = {3, 4, 16, 17, 16, 4}, YCOOR = {3, 2, 2, 3, 4, 4};
    
    public SevenSegment(){
        this.mValueOn = 10;
        this.setPrefSize(1000*DIMX, 1000*DIMY);
        this.getChildren().add(mCanvas);
    }
    
    public SevenSegment(int num){
        this();
        if (num > 10 || num < 0){
            this.mValueOn = 10;
        }
        else {
            this.mValueOn = num;
        }
        
    }
    
    @Override
    protected void layoutChildren(){
        super.layoutChildren();
        double availableHeight = this.getHeight();
        double availableWidth = this.getWidth();
        double calculatedWidth = availableHeight*ASPECT;
        double calculatedHeight = availableWidth*(1/ASPECT);
        if (calculatedHeight > availableHeight){
            mCanvas.setHeight(availableHeight);
            mCanvas.setWidth(calculatedWidth);
        }
        else{
            mCanvas.setHeight(calculatedHeight);
            mCanvas.setWidth(availableWidth);
        }
        layoutInArea(mCanvas, 0, 0, super.getWidth(), super.getHeight(), this.getBaselineOffset(), HPos.CENTER, VPos.CENTER);
        this.draw();
    }
    
    public void draw(){
        GraphicsContext gc = mCanvas.getGraphicsContext2D();
        gc.save(); setArray(mValueOn);
        gc.clearRect(0, 0, mCanvas.getWidth(), mCanvas.getHeight());
        double scalex = mCanvas.getWidth()/DIMX;
        double scaley = mCanvas.getHeight()/DIMY;
        gc.scale(scalex, scaley);
        gc.translate(0, 0);
        
        //Segment 0
        gc.setFill(mSegmentArr[0] == false ? OFF : ON );
        gc.fillPolygon(XCOOR, YCOOR, 6);  
        
        gc.save();
        //Segment 1
        gc.translate(20, 0);
        gc.rotate(90.0);
        gc.setFill(mSegmentArr[1] == false ? OFF : ON );
        gc.fillPolygon(XCOOR, YCOOR, 6);
        gc.restore();
        
        gc.save();
        //Segment 2
        gc.translate(0, 14);
        gc.setFill(mSegmentArr[2] == false ? OFF : ON );
        gc.fillPolygon(XCOOR, YCOOR, 6);
        gc.restore();
        
        gc.save();
        //Segment 3
        gc.rotate(90.0);
        gc.translate(0, -6);
        gc.setFill(mSegmentArr[3] == false ? OFF : ON );
        gc.fillPolygon(XCOOR, YCOOR, 6);
        gc.restore();
        
        gc.save();
        //Segment 4
        gc.rotate(90.0);
        gc.translate(14, -6);
        gc.setFill(mSegmentArr[4] == false ? OFF : ON );
        gc.fillPolygon(XCOOR, YCOOR, 6);
        gc.restore();
        
        gc.save();
        //Segment 5
        gc.rotate(90.0);
        gc.translate(14, -20);
        gc.setFill(mSegmentArr[5] == false ? OFF : ON );
        gc.fillPolygon(XCOOR, YCOOR, 6);
        gc.restore();
        
        gc.save();
        //Segment 6
        gc.translate(0, 28);
        gc.setFill(mSegmentArr[6] == false ? OFF : ON );
        gc.fillPolygon(XCOOR, YCOOR, 6);
        gc.restore();
        
        gc.restore();
        
    }
    
    public int getMValueOn(){
        return mValueOn;
    }
    
    public void setMValueOn(int val){
        if (val > 10 || val < 0){
            this.mValueOn = 10;
        }
        else {
            this.mValueOn = val;
        }
    }
    
    private void setArray(int i){
        if (i <= 10 && i >= 0){
            mSegmentArr = mValues[i];
        }
    }
    
}
