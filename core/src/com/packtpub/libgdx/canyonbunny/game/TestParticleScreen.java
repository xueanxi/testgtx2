package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.packtpub.libgdx.canyonbunny.utils.Constants;
import com.packtpub.libgdx.canyonbunny.utils.Logs;

/**
 * ParticleEdit
 */

public class TestParticleScreen extends ScreenAdapter {
    private static final String TAG = "=TestScreen";

    Image image;
    MyPaticleActor paticle1;
    SpriteBatch batch;
    ParticleEffect particleEffect;


    public TestParticleScreen() {
        super();
        //paticleStage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH,Constants.VIEWPORT_GUI_HEIGHT));
         particleEffect = new ParticleEffect();
        //particleEffect.load(Gdx.files.internal("particle/one/bluestart.p"),Gdx.files.internal("particle/one"));
        //particleEffect.load(Gdx.files.internal("particle/one/yellowstart.p"),Gdx.files.internal("particle/one"));
        particleEffect.load(Gdx.files.internal("particle/one/fire.p"),Gdx.files.internal("particle/one"));
        //paticle1 = new MyPaticleActor(particleEffect);
        //paticle1.setPosition(400,300);

        //particleEffect.start();
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        //super.render(delta);

        batch.begin();
        particleEffect.update(delta);
        particleEffect.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        //paticleStage.getViewport().update(width,height);
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

    class MyPaticleActor extends Actor{
        ParticleEffect effect;
        Vector2 position;

        public MyPaticleActor(ParticleEffect effect){
            this.effect = effect;
            this.effect.setPosition(400,300);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            this.effect.draw(batch);
        }

        @Override
        public void act(float delta) {
            //super.act(delta);

            if(!isStart){
                this.effect.update(delta);
                this.effect.start();
                isStart = true;
            }
        }
        boolean isStart = false;

        public ParticleEffect getEffect(){
            return this.effect;
        }

        public void updatePosition(Vector2 position){
            this.position = position;
        }
    }
}
