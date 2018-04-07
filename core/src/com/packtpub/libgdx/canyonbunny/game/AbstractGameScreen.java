package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.packtpub.libgdx.canyonbunny.utils.Assets;

/**
 * Created by user on 3/27/18.
 */

public abstract class AbstractGameScreen implements Screen{

    public DirectedGame game;

    public AbstractGameScreen(DirectedGame game){
        this.game = game;
    }

    public abstract InputProcessor getInputProcessor();

    public void show() {

    }


    public void render(float delta) {

    }


    public void resize(int width, int height) {

    }


    public void pause() {

    }


    public void resume() {

    }


    public void hide() {

    }


    public void dispose() {
        Assets.instance.dispose();
    }
}
