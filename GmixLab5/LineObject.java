/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmixlab5;

import java.io.IOException;
import java.io.Serializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Gingervitis
 */
public class LineObject implements Serializable{
    
    private transient Point2D mPointFrom, mPointTo;
    private double mWidth;
    private transient Color mColor;
    
    public LineObject(Point2D pointFrom, Point2D pointTo, double width, Color color){
        mPointFrom = pointFrom; mPointTo = pointTo;
        mWidth = width;
        mColor = color;
    }
    
    public void draw(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(mColor);
        gc.setLineWidth(mWidth);
        gc.strokeLine(mPointFrom.getX(), mPointFrom.getY(), mPointTo.getX(), mPointTo.getY());
    }
    
    private void writeObject(java.io.ObjectOutputStream out) throws IOException{
        out.defaultWriteObject();
        out.writeDouble(mColor.getRed());
        out.writeDouble(mColor.getGreen());
        out.writeDouble(mColor.getBlue());
        
        out.writeDouble(mPointFrom.getX());
        out.writeDouble(mPointFrom.getY());
        
        out.writeDouble(mPointTo.getX());
        out.writeDouble(mPointTo.getY());
        
        //out.writeDouble(mWidth);
    }
    
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        double r = in.readDouble();
        double g = in.readDouble();
        double b = in.readDouble();
        mColor = new Color(r, g, b, 1.0);
        
        double X = in.readDouble();
        double Y = in.readDouble();
        
        mPointFrom = new Point2D(X, Y);
        
        X = in.readDouble();
        Y = in.readDouble();
        
        mPointTo = new Point2D(X, Y);
        
        //mWidth = in.readDouble();
    }
    
}
