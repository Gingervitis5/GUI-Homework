/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmixlab7;

import java.util.prefs.Preferences;

/**
 *
 * @author Gingervitis
 */
public class ApplicationSettings {
    
    private boolean mDateDisplayed; //True for date/time, false for string
    private boolean mItalicsOn;
    private boolean mBoldOn;
    private int mFontSize;
    private String mString;
    private static ApplicationSettings mSettings;
    private Preferences mPref = Preferences.userNodeForPackage(getClass());
    
    private ApplicationSettings(boolean dateDisplayed,
                                boolean italicsOn,
                                boolean boldOn,
                                int fontSize,
                                String string){
        this.mDateDisplayed = dateDisplayed;
        this.mItalicsOn = italicsOn;
        this.mBoldOn = boldOn;
        this.mFontSize = fontSize;
        this.mString = string;
    }
    
    public static ApplicationSettings createSettings(boolean dateDisplayed,
                                                boolean italicsOn,
                                                boolean boldOn,
                                                int fontSize,
                                                String string){
        if (mSettings == null){
            mSettings = new ApplicationSettings(dateDisplayed,italicsOn,boldOn,fontSize,string);
            return mSettings;
        }
        else {
            return mSettings;
        }
    }
    
    public boolean getDateDisplayed(){
        return mDateDisplayed;
    }
    
    public void setDateDisplayed(boolean set){
        this.mDateDisplayed = set;
    }
    
    public boolean getItalicsOn(){
        return mItalicsOn;
    }
    
    public void setItalicsOn(boolean set){
        this.mItalicsOn = set;
    }
    
    public boolean getBoldOn(){
        return mBoldOn;
    }
    
    public void setBoldOn(boolean set){
        this.mBoldOn = set;
    }
    
    public int getFontSize(){
        return mFontSize;
    }
    
    public void setFontSize(int size){
        this.mFontSize = size;
    }
    
    public String getString(){
        return mString;
    }
    
    public void setString(String string){
        this.mString = string;
    }
    
    public void storePreferences(Class c){
        mPref.putBoolean("DateDisplayed", mDateDisplayed);
        mPref.putBoolean("ItalicsOn", mItalicsOn);
        mPref.putBoolean("BoldOn", mBoldOn);
        mPref.putInt("FontSize", mFontSize);
        mPref.put("String", mString);
    }
    
    public void readPreferences(Class c){
        mDateDisplayed = mPref.getBoolean("DateDisplayed", true);
        mItalicsOn = mPref.getBoolean("ItalicsOn", false);
        mBoldOn = mPref.getBoolean("BoldOn", false);
        mFontSize = mPref.getInt("FontSize", 20);
        mString = mPref.get("String", "Put something here");
    }
}
