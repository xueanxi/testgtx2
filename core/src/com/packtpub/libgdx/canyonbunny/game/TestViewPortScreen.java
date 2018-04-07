package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.packtpub.libgdx.canyonbunny.utils.Constants;
import com.packtpub.libgdx.canyonbunny.utils.Logs;

/**
 * 适配不同分辨率的关键就是，保证自己的画面不变形。
 * 要保证自己画面不变形，可以这样实现:
 * 1.固定自己画面的高度，然后让自己画面的宽度布局随屏幕尺寸而变化。
 * 2.考虑自己的画面布局，如果是开发一款手机横屏的游戏，一般使用宽高比800:480比较通用。
 * 3.宽度相关的重要界面尽量使用屏幕宽度百分比计算，避免超出界面，用户看不到，不过现在的手机是越来越长，应该不会出现看不到的情况。
 */

public class TestViewPortScreen extends AbstractGameScreen {
    private static final String TAG = "=TestActionScreen";
    Stage stage;
    Image image;
    Viewport viewport;

    int screenWidth = 800;
    int screenHeight = 480;

    public TestViewPortScreen(DirectedGame game) {
        super(game);
        viewport = new StretchViewport(screenWidth, screenHeight);
        //viewport = new FillViewport(screenWidth,screenHeight);
        //viewport = new FitViewport(screenWidth,screenHeight);
        stage = new Stage(viewport);
        Pixmap pixmap = new Pixmap(300, 300, Pixmap.Format.RGB888);
        pixmap.setColor(Color.BLUE);
        pixmap.fill();

        image = new Image(new Texture(pixmap));
        stage.addActor(image);
    }

    @Override
    public void render(float delta) {
        //super.render(delta);
        Gdx.gl.glClearColor(0, 255, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
       /* Logs.d(TAG,"resize w:"+width+" h:"+height);
        viewport = new StretchViewport(5, 5);
        stage.setViewport(viewport);*/

/*        stage.getCamera().viewportWidth = screenHeight / height * width;
        stage.getCamera().update();*/

        stage.getCamera().viewportHeight = screenHeight;
        stage.getCamera().viewportWidth = (screenHeight/ (float)height) * (float)width;
        stage.getCamera().position.set(stage.getCamera().viewportWidth / 2,stage.getCamera().viewportHeight / 2, 0);
        stage.getCamera().update();

    }

    @Override
    public InputProcessor getInputProcessor() {
        return null;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
