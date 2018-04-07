package com.packtpub.libgdx.canyonbunny.interfaces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by anxi.xue on 2018/4/7.
 */

public interface ScreenTransition {
    public float getDuration();
    public void render(SpriteBatch batch, Texture currScreen,Texture nextScreen,float alpha);
}
