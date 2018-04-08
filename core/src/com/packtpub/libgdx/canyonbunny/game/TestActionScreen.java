package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.packtpub.libgdx.canyonbunny.utils.Assets;
import com.packtpub.libgdx.canyonbunny.utils.Constants;
import com.packtpub.libgdx.canyonbunny.utils.Logs;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by user on 3/28/18.
 */

public class TestActionScreen extends AbstractGameScreen {
    private static final String TAG = "=TestActionScreen";
    private static final float VIEWPORT_GUI_HEIGHT = 480.0f;
    private static final float VIEWPORT_GUI_WIDTH = 800.0f;
    Stage stage;
    Image image;
    StretchViewport viewport;

    public TestActionScreen(DirectedGame game) {
        super(game);
        viewport = new StretchViewport(VIEWPORT_GUI_WIDTH, VIEWPORT_GUI_HEIGHT);
        stage = new Stage(viewport);
        rebuild();

    }

    private void rebuild(){
        stage.clear();


        TextButton button = new TextButton("button", Assets.instance.defaultSkin);
        button.setWidth(100);
        button.setHeight(100);
        button.setPosition(VIEWPORT_GUI_WIDTH-100,VIEWPORT_GUI_HEIGHT-100);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rebuild();
            }
        });
        stage.addActor(button);

        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGB888);
        pixmap.setColor(Color.BLUE);
        pixmap.fill();

        image = new Image(new Texture(pixmap));
        image.setPosition(100, 100);

        //SequenceAction s = sequence(moveTo(200, 100,0.5f),moveTo(200, 200,0.5f),moveTo(200, 100,0.5f),moveTo(100, 100,0.5f));
        //SequenceAction s = sequence(fadeOut(1f));
        //SequenceAction s = sequence(scaleTo(0,0,1));
        //SequenceAction s = sequence(scaleTo(0.5f,0.5f,1),delay(1),scaleTo(0,0,0.5f));
        //SequenceAction s = sequence(parallel(scaleTo(0.5f,0.5f,1),moveTo(300,300,1f)));
        //SequenceAction s = sequence(moveBy(200, 200, 5, Interpolation.fade));
        //SequenceAction s = sequence(moveBy(200, 200, 5, Interpolation.elastic));
        //SequenceAction s = sequence(moveBy(200, 200, 5, Interpolation.elasticIn));
        //SequenceAction s = sequence(moveBy(200, 200, 5, Interpolation.swing));
        //image.addAction(s);

        MyBunnyHead bunnyHead = new MyBunnyHead(Assets.instance.bunnyHead.getAnimation1());
        stage.addActor(bunnyHead);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        stage.getCamera().viewportHeight = VIEWPORT_GUI_HEIGHT;
        stage.getCamera().viewportWidth = VIEWPORT_GUI_HEIGHT / height * width;
        stage.getCamera().position.set(stage.getCamera().viewportWidth/2,stage.getCamera().viewportHeight/2,0);
        stage.getCamera().update();
    }

    @Override
    public InputProcessor getInputProcessor() {
        if(stage != null){
            return stage;
        }
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
        float statusTime = 0;
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
