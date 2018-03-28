package com.packtpub.libgdx.canyonbunny.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by anxi.xue on 2018/3/27.
 */

public class GamePreferences {
    public static final String TAG = "=GamePreferences";
    public boolean sound;
    public boolean music;
    public float volSound;
    public float volMusic;
    public int charSkin;
    public boolean showFpsCounter;
    private static Preferences prefs;
    private static GamePreferences mInstance;

    // singleton: prevent instantiation from other classes
    private GamePreferences() {
        prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
    }

    public static GamePreferences getInstance(){
        if(mInstance == null){
            mInstance = new GamePreferences();
        }
        return mInstance;
    }

    public void load() {
        sound = prefs.getBoolean("sound", true);
        music = prefs.getBoolean("music", true);
        volSound = MathUtils
                .clamp(prefs.getFloat("volSound", 0.5f), 0.0f, 1.0f);
        volMusic = MathUtils
                .clamp(prefs.getFloat("volMusic", 0.5f), 0.0f, 1.0f);
        charSkin = MathUtils.clamp(prefs.getInteger("charSkin", 0), 0, 2);
        showFpsCounter = prefs.getBoolean("showFpsCounter", false);
    }

    public void save() {
        prefs.putBoolean("sound", sound);
        prefs.putBoolean("music", music);
        prefs.putFloat("volSound", volSound);
        prefs.putFloat("volMusic", volMusic);
        prefs.putInteger("charSkin", charSkin);
        prefs.putBoolean("showFpsCounter", showFpsCounter);
        prefs.flush();
    }
}
