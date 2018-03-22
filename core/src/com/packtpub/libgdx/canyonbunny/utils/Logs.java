package com.packtpub.libgdx.canyonbunny.utils;

import com.badlogic.gdx.Gdx;

/**
 * Created by user on 3/22/18.
 */

public class Logs {

    public static void l(String TAG,String log){
        Gdx.app.log(TAG,log);
    }

    public static void d(String TAG,String log){
        Gdx.app.debug(TAG,log);
    }

    public static void e(String TAG,String log){
        Gdx.app.error(TAG,log);
    }
}
