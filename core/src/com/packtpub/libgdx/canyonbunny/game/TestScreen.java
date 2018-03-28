package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.packtpub.libgdx.canyonbunny.utils.Constants;
import com.packtpub.libgdx.canyonbunny.utils.Logs;

/**
 * Created by user on 3/28/18.
 */

public class TestScreen extends ScreenAdapter {
    private static final String TAG = "=TestScreen";
    Stage testStage;
    Image image;
    StretchViewport viewport;

    public TestScreen() {
        super();
        viewport = new StretchViewport(Constants.VIEWPORT_GUI_WIDTH,Constants.VIEWPORT_GUI_HEIGHT);
        //viewport = new StretchViewport(600,400);
        testStage = new Stage(viewport);
        image = new Image(new Texture(Gdx.files.internal("image/canyonbunny-bg.png")));
        //imgBackground = new Image(skinCanyonBunny, "background");
        testStage.addActor(image);
    }

    @Override
    public void render(float delta) {
        //super.render(delta);
        testStage.act();
        testStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        Logs.d(TAG,"resize w:"+width+" h:"+height);
        //testStage.setViewport(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT));
        viewport = new StretchViewport(5, 5);
        testStage.setViewport(viewport);
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
