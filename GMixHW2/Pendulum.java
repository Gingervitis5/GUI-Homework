/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmixhw2;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 *
 * @author Gingervitis
 */
public class Pendulum extends Region{
    
    private final Image BACKIMAGE = new Image("gmixhw2/EWU.jpeg");
    private final Image PENDULUM_IMAGE = new Image("gmixhw2/Symbol.jpeg");
    private final double DIMX = 20, DIMY = 14;
    private final double ASPECT = DIMX/DIMY; 
    private static final double mArm = 10;
    private Canvas mCanvas = new Canvas(BACKIMAGE.getWidth(), BACKIMAGE.getHeight());
    private double mTheta, mAngularVelocity;
    private long mPreviousTime;
    private final AnimationTimer mTimer;
    private boolean mIsPaused;
    private SimpleDoubleProperty mLoc = new SimpleDoubleProperty(),
                                mMinX = new SimpleDoubleProperty(),
                                mMaxX = new SimpleDoubleProperty();
    
    public Pendulum(){
        mAngularVelocity = 0;
        mTheta = -45*(Math.PI/180);
        mIsPaused = true;
        mTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onTimer(now);
            }
        };
        this.setPrefSize(30*DIMX, 30*DIMY);
        this.getChildren().add(mCanvas);
    }
    
    public SimpleDoubleProperty locProperty(){
        return mLoc;
    }
    
    public SimpleDoubleProperty minXProperty(){
        return mMinX;
    }
    
    public SimpleDoubleProperty maxXProperty(){
        return mMaxX;
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
        draw();
    }
    
    private void updatePhysics(double elapsed){
        mLoc.set(mArm*Math.sin(mTheta));
        double angularAcceleration = -((9.80665/10)*Math.sin(mTheta));
        mAngularVelocity = angularAcceleration*elapsed + mAngularVelocity;
        mTheta = (mAngularVelocity*elapsed) + mTheta;
        
        if (mLoc.doubleValue() < mMinX.doubleValue()) { 
            mMinX.set(mLoc.doubleValue());
            mLoc.set(mMinX.doubleValue());
        }
        if (mLoc.doubleValue() > mMaxX.doubleValue()) { 
            mMaxX.set(mLoc.doubleValue()); 
            mLoc.set(mMaxX.doubleValue());
        }
    }
    
    private void draw(){
        GraphicsContext gc = mCanvas.getGraphicsContext2D();
        drawBackground();
        gc.save();
        gc.translate(mCanvas.getWidth()/2, 0);
        gc.scale(mCanvas.getWidth()/DIMX, mCanvas.getHeight()/DIMY); 
        gc.rotate(-Math.toDegrees(mTheta));
        gc.setStroke(Color.WHITE); gc.setLineWidth(0.20);
        gc.strokeLine(0,0,0,mArm);
        gc.beginPath();
        gc.arc(0, mArm, 1.5, 1.5, 0, 360);
        gc.closePath();
        gc.clip();
        gc.drawImage(PENDULUM_IMAGE, -1.5, 8.5, 3, 3);
        gc.restore();    
    }
    
    private void drawBackground(){
        GraphicsContext gc = mCanvas.getGraphicsContext2D();
        gc.drawImage(BACKIMAGE, 0, 0, mCanvas.getWidth(), mCanvas.getHeight());
    }
    
    private void onTimer(long now){
        now = System.currentTimeMillis();
        double elapsed = (now-mPreviousTime);
        if (!mIsPaused){
            draw();
            updatePhysics(elapsed/1000);
            mPreviousTime = now;
        }
    }
    
    public void stopTimer(){
        mTimer.stop();
        mIsPaused = true;
    }
    public void startTimer(){
        mTimer.start();
         mPreviousTime = System.currentTimeMillis();
        mIsPaused = false;
    }
    
}