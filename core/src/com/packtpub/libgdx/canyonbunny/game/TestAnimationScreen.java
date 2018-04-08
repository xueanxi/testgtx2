package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.packtpub.libgdx.canyonbunny.utils.Assets;
import com.packtpub.libgdx.canyonbunny.utils.Logs;

import sun.rmi.runtime.Log;

/**
 * Created by user on 3/28/18.
 */

public class TestAnimationScreen extends AbstractGameScreen {
    private static final String TAG = "=TestAnimationScreen";
    private static final float VIEWPORT_GUI_HEIGHT = 480.0f;
    private static final float VIEWPORT_GUI_WIDTH = 800.0f;
    SpriteBatch batch;
    Animation animation;
    float statuTime = 0;

    public TestAnimationScreen(DirectedGame game) {
        super(game);
        batch = new SpriteBatch();
        animation = Assets.instance.bunnyHead.getAnimation1();
        Logs.d(TAG,"animation.getFrameDuration() = "+animation.getFrameDuration());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        statuTime += Gdx.graphics.getDeltaTime();
        Logs.d(TAG,"statuTime = "+statuTime);

        TextureRegion tr = (TextureRegion) animation.getKeyFrame(statuTime,true);

        batch.begin();
        batch.draw(tr,100,100);
        batch.end();


    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
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

    class MyBunnyHead extends Actor{
        Animation animation;
        int statusTime = 0;
        MyBunnyHead(Animation animation){
            this.animation = animation;
        }

        @Override
        public void act(float delta) {
            super.act(delta);
            statusTime+=delta;
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            TextureRegion reg = (TextureRegion) animation.getKeyFrame(statusTime,true);
/*            batch.draw(reg,
                    this.getX(),this.getY(),getOriginX(),getOriginY(),
                    this.getWidth(),this.getHeight(),
                    this.getScaleX(),this.getScaleY(),
                    this.getRotation(),
                    reg.getRegionX(),reg.getRegionY(),
                    reg.getRegionWidth(),reg.getRegionHeight(),
                    false,);*/
            batch.draw(reg,200,200);
        }
    }
}
