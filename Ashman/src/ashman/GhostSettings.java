package ashman;

import java.io.Serializable;
import java.util.prefs.Preferences;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gingervitis
 */
public class GhostSettings implements Serializable{
    private int mNumGhosts, numAddGhosts;
    private int mSpeed;
    private static GhostSettings settings;
    private Preferences mPref = Preferences.userNodeForPackage(getClass());
    
    private GhostSettings(int num, int speed){
        this.mNumGhosts = num;
        this.mSpeed = speed;
        settings = null;
    }
    
    public static GhostSettings createSettings(int num, int speed){
        if (settings == null){
            settings = new GhostSettings(num, speed);
            return settings;
        }
        else {
            return settings;
        }
    }
    
    public int getSpeed(){
        return mSpeed;
    }
    
    public void setSpeed(int s){
        mSpeed = s;
    }
    
    public int getNumGhosts(){
        return mNumGhosts;
    }
    
    public void setNumGhosts(int n){
        mNumGhosts = n;
    }
    
    public int getNumAddGhosts(){
        return numAddGhosts;
    }
    
    public void setNumAddGhosts(int n){
        numAddGhosts = n;
    }
    
    public void storePreferences(Class c){
        mPref.putInt("Speed", mSpeed);
        mPref.putInt("Ghosts", mNumGhosts);
        mPref.putInt("AddGhosts", numAddGhosts);
    }
    
    public void readPreferences(Class c){
        mSpeed = mPref.getInt("Speed", 1);
        mNumGhosts = mPref.getInt("Ghosts", 2);
        numAddGhosts = mPref.getInt("AddGhosts", numAddGhosts);
    }
}
