package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.packtpub.libgdx.canyonbunny.utils.Assets;

/**
 * Created by user on 3/27/18.
 */

public class AbstractGameScreen implements Screen{

    public Game game;

    public AbstractGameScreen(Game game){
        this.game = game;
    }

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
        Assets.getInstance();
    }


    public void dispose() {
        Assets.getInstance().dispose();
    }
}
