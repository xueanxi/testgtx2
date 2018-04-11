package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.packtpub.libgdx.canyonbunny.interfaces.INotify;
import com.packtpub.libgdx.canyonbunny.interfaces.ScreenTransition;

/**
 * Created by anxi.xue on 2018/4/7.
 */

public class DirectedGame implements ApplicationListener {
    private boolean isInit = false;
    private AbstractGameScreen currGameScreen;
    private AbstractGameScreen nextGameScreen;
    private FrameBuffer currFbo;
    private FrameBuffer nextFbo;
    private SpriteBatch batch;
    private float t;
    private ScreenTransition screenTransition;

    public INotify notify;

    public DirectedGame(){
        this.notify = null;
    }

    public DirectedGame(INotify notify){
        this.notify = notify;
    }

    public INotify getNotify(){
        if(this.notify !=null){
            return notify;
        }else{
            return null;
        }
    }

    public void setScreen(AbstractGameScreen screen){
        this.setScreen(screen,null);
    }

    public void setScreen(AbstractGameScreen screen,ScreenTransition screenTransition){
        float deltaTime = Math.min(Gdx.graphics.getDeltaTime(),1.0f/60.0f);
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        if(!isInit){
            currFbo = new FrameBuffer(Pixmap.Format.RGB888,width,height,false);
            nextFbo = new FrameBuffer(Pixmap.Format.RGB888,width,height,false);
            batch = new SpriteBatch();
            isInit = true;
        }

        nextGameScreen = screen;
        nextGameScreen.show();
        nextGameScreen.resize(width,height);
        nextGameScreen.render(0);
        if(currGameScreen != null){
            currGameScreen.pause();
        }

        nextGameScreen.pause();
        Gdx.input.setInputProcessor(null);
        this.screenTransition = screenTransition;
        t=0;
    }


    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {
        if (currGameScreen != null)
            currGameScreen.resize(width, height);
        if (nextGameScreen != null)
            nextGameScreen.resize(width, height);
    }

    @Override
    public void render() {
        float deltaTime = Math.min(Gdx.graphics.getDeltaTime(),1.0f/60.0f);
        if(nextGameScreen == null ){
            if(currGameScreen!=null) currGameScreen.render(deltaTime);
        }else{
            float duration = 0;
            if(screenTransition != null){
                duration = screenTransition.getDuration();
            }
            t = Math.min(t + deltaTime, duration);
            if (screenTransition == null || t >= duration) {
                // no transition effect set or transition has just finished
                if (currGameScreen != null){
                    currGameScreen.hide();
                }
                nextGameScreen.resume();
                // enable input for next screen
                Gdx.input.setInputProcessor(nextGameScreen.getInputProcessor());
                // switch screens
                currGameScreen = nextGameScreen;
                nextGameScreen = null;
                screenTransition = null;
            } else {
                // render screens to FBOs
                currFbo.begin();
                if (currGameScreen != null)
                    currGameScreen.render(deltaTime);
                currFbo.end();
                nextFbo.begin();
                nextGameScreen.render(deltaTime);
                nextFbo.end();
                // render transition effect to screen
                float alpha = t / duration;
                screenTransition.render(batch, currFbo.getColorBufferTexture(),
                        nextFbo.getColorBufferTexture(), alpha);
            }
        }
    }

    @Override
    public void pause() {
        if(currGameScreen!= null)
            currGameScreen.pause();
    }

    @Override
    public void resume() {
        if(currGameScreen!= null){
            currGameScreen.resume();
        }
    }

    @Override
    public void dispose() {
        if(currGameScreen !=null){
            currGameScreen.hide();
        }

        if(nextGameScreen != null){
            nextGameScreen.hide();
        }

        if(isInit){
            currFbo.dispose();
            currGameScreen = null;

            nextFbo.dispose();
            nextGameScreen = null;

            batch.dispose();
            isInit = false;

        }
    }
}
