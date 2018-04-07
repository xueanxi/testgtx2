package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    private static final String TAG = "=TestActionScreen";

    Image image;
    MyPaticleActor paticle1;
    SpriteBatch batch;
    ParticleEffect particleEffect;
    OrthographicCamera camera;


    public TestParticleScreen() {
        super();
        //paticleStage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH,Constants.VIEWPORT_GUI_HEIGHT));
         particleEffect = new ParticleEffect();
        //particleEffect.load(Gdx.files.internal("particle/one/bluestart.p"),Gdx.files.internal("particle/one"));
        particleEffect.load(Gdx.files.internal("particle/two/fire.p"),Gdx.files.internal("particle/two"));
      // particleEffect.load(Gdx.files.internal("particle/one/yellowstart.p"),Gdx.files.internal("particle/one"));
        //particleEffect.load(Gdx.files.internal("particle/one/fire.p"),Gdx.files.internal("particle/one"));
        //paticle1 = new MyPaticleActor(particleEffect);
        //paticle1.setPosition(400,300);

        particleEffect.setPosition(100,100);
        //particleEffect.start();

        batch = new SpriteBatch();

        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        camera.position.set(0,0,0);
        camera.setToOrtho(false);
        camera.update();
    }

    private void handleInputGame(float deltaTime) {
        //if (cameraHelper.hasTarget(level.buuyhead)) {
        // Player Movement
        if (Gdx.input.isTouched()){
            particleEffect.setPosition(Gdx.input.getX(),Gdx.graphics.getHeight()-Gdx.input.getY());
            Logs.d(TAG,"handleInputGame X:"+Gdx.input.getX()+" Y:"+Gdx.input.getY());
            particleEffect.reset();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            particleEffect.allowCompletion();
        }
    }

    @Override
    public void render(float delta) {
        //super.render(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInputGame(delta);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        particleEffect.start();
        particleEffect.update(delta);
        particleEffect.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        Logs.d(TAG,"reside w:"+width+" h:"+height);
        camera.update();
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
            this.effect.update(delta);
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
