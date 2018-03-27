package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.packtpub.libgdx.canyonbunny.WorldController;
import com.packtpub.libgdx.canyonbunny.WorldRenderer;

/**
 * Created by user on 3/27/18.
 */

public class GameScreen extends  AbstractGameScreen {
    // http://www.cnblogs.com/mignet/p/Learning_Libgdx_Game_Development_01.html
    WorldController worldController;
    WorldRenderer worldRenderer;

    //
    boolean isPause = false;

    public GameScreen(Game game) {
        super(game);
        worldController = new WorldController(game);
        worldRenderer = new WorldRenderer(worldController);
        isPause = false;
    }

    @Override
    public void render (float detla) {
        if(!isPause){
            worldController.update(detla);
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        //super.resize(width, height);
        worldRenderer.resize(width,height);
    }

    @Override
    public void dispose () {
        worldRenderer.dispose();
    }

    @Override
    public void pause() {
        super.pause();
        isPause = true;
    }

    @Override
    public void resume() {
        super.resume();
        isPause = false;
    }

    @Override
    public void show() {
        worldController = new WorldController(game);
        worldRenderer = new WorldRenderer(worldController);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void hide() {
        worldRenderer.dispose();
        Gdx.input.setCatchBackKey(false);
    }
}
