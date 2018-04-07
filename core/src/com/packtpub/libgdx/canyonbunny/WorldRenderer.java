package com.packtpub.libgdx.canyonbunny;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.packtpub.libgdx.canyonbunny.utils.Assets;
import com.packtpub.libgdx.canyonbunny.utils.Constants;

/**
 * Created by user on 3/22/18.
 */

public class WorldRenderer implements Disposable {
    private static final String TAG = "=WorldRenderer";

    private static final boolean DEBUG_DRAW_BOX2D_WORLD = false;
    Box2DDebugRenderer box2DDebugRenderer;
    private ShaderProgram shaderMonochrome;

    OrthographicCamera camera;
    private OrthographicCamera cameraGUI;
    SpriteBatch batch;
    WorldController worldController;




    public WorldRenderer(WorldController worldController){
        this.worldController = worldController;
        init();
    }

    public void render(){
        renderWorld(batch);
        renderGui(batch);
    }


    private void renderGui(SpriteBatch batch) {
        batch.setProjectionMatrix(cameraGUI.combined);
        //Logs.d(TAG,"bunny head position "+worldController.level.buuyhead.position);

        batch.begin();
        // draw collected gold coins icon + text
        // (anchored to top left edge)
        renderGuiScore(batch);
        // draw extra lives icon + text (anchored to top right edge)
        renderGuiExtraLive(batch);


        renderGuiFeatherPowerup(batch);
        // draw FPS text (anchored to bottom right edge)
        renderGuiFpsCounter(batch);

        renderGuiGameOverMessage(batch);
        batch.end();
    }

    private void renderWorld (SpriteBatch batch) {
        worldController.cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        worldController.level.render(batch);
        batch.end();

        if(DEBUG_DRAW_BOX2D_WORLD){
            box2DDebugRenderer.render(worldController.b2world,camera.combined);
        }
        /*shaderMonochrome = new ShaderProgram(
                Gdx.files.internal(Constants.shaderMonochromeVertex),
                Gdx.files.internal(Constants.shaderMonochromeFragment));
        if (!shaderMonochrome.isCompiled()) {
            String msg = "Could not compile shader program: "
                    + shaderMonochrome.getLog();
            throw new GdxRuntimeException(msg);
        }*/
    }



    public void resize(int width, int height){
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        camera.update();

        cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
        cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT/ (float)height) * (float)width;
        cameraGUI.position.set(cameraGUI.viewportWidth / 2,cameraGUI.viewportHeight / 2, 0);
        cameraGUI.update();
    }

    private void init(){
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        camera.position.set(0,0,0);
        camera.update();

        cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH,Constants.VIEWPORT_GUI_HEIGHT);
        cameraGUI.position.set(0, 0, 0);
        cameraGUI.setToOrtho(false); // flip y-axis
        cameraGUI.update();

        box2DDebugRenderer = new Box2DDebugRenderer();
    }
    private void renderGuiExtraLive(SpriteBatch batch) {
        float x = cameraGUI.viewportWidth - 50 - Constants.LIVES_START * 50;
        float y = -15;
        for (int i = 0; i < Constants.LIVES_START; i++) {
            if (worldController.lives <= i)
                batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
            batch.draw(Assets.instance.findTextureByName(Constants.AtlasNames.BUNNY_HEAD), x + i * 50, y, 50, 50, 120,
                    100, 0.35f, -0.35f, 0);
            batch.setColor(1, 1, 1, 1);
        }
        if (worldController.lives >= 0
                && worldController.livesVisual > worldController.lives) {
            int i = worldController.lives;
            float alphaColor = Math.max(0, worldController.livesVisual
                    - worldController.lives - 0.5f);
            float alphaScale = 0.35f * (2 + worldController.lives - worldController.livesVisual) * 2;
            float alphaRotate = -45 * alphaColor;
            batch.setColor(1.0f, 0.7f, 0.7f, alphaColor);
            batch.draw(Assets.instance.findTextureByName(Constants.AtlasNames.BUNNY_HEAD), x + i * 50, y, 50, 50, 120,
                    100, alphaScale, -alphaScale, alphaRotate);
            batch.setColor(1, 1, 1, 1);
        }
    }

    private void renderGuiScore(SpriteBatch batch) {

        TextureRegion reg = Assets.instance.findTextureByName(Constants.AtlasNames.ITEM_GOLD_COIN);
        int width = reg.getRegionWidth();
        int height = reg.getRegionHeight();
        float x = 15;
        float y = cameraGUI.viewportHeight - height -5;
        batch.draw(reg,
                x, y,
                width/2, height/2,
                width, height,
                0.6f, 0.6f, 0);
        Assets.instance.getBitmapFont(2).draw(batch,"" + worldController.score, x + width, y + height*0.5f);
    }


    /**
     * 兔子捡到羽毛的时间
     * @param batch
     */
    private void renderGuiFeatherPowerup(SpriteBatch batch) {
        float x = 10;
        float y = 10;
        float timeLeftFeatherPowerup = worldController.level.buuyhead.timeLeftFeatherPowerup;
        if (timeLeftFeatherPowerup > 0) {
            // Start icon fade in/out if the left power-up time
            // is less than 4 seconds. The fade interval is set
            // to 5 changes per second.
            if (timeLeftFeatherPowerup < 4) {
                if (((int) (timeLeftFeatherPowerup * 5) % 2) != 0) {
                    batch.setColor(1, 1, 1, 0.5f);
                }
            }
            batch.draw(Assets.instance.findTextureByName(Constants.AtlasNames.ITEM_FEATHER), x, y, 0, 0, 60, 60,
                    1,1, 0);
            batch.setColor(1, 1, 1, 1);
            Assets.instance.getBitmapFont(2).draw(batch, ""+ (int) timeLeftFeatherPowerup, x+60 , y+30 );
        }
    }

    private void renderGuiGameOverMessage(SpriteBatch batch) {
        if (worldController.isGameOver()) {
            float x = cameraGUI.viewportWidth / 2;
            float y = cameraGUI.viewportHeight / 2;
            BitmapFont fontGameOver = Assets.instance.getBitmapFont(3);
            fontGameOver.setColor(1, 0.75f, 0.25f, 1);
            fontGameOver.draw(batch, "GAME OVER", x, y,300, Align.center,true);
            fontGameOver.setColor(1, 1, 1, 1);
        }
    }

    private void renderGuiFpsCounter(SpriteBatch batch) {
        float x = cameraGUI.viewportWidth - 55;
        float y =  15;
        int fps = Gdx.graphics.getFramesPerSecond();
        BitmapFont fpsFont = Assets.instance.getBitmapFont(2);
        if (fps >= 45) {
            // 45 or more FPS show up in green
            fpsFont.setColor(0, 1, 0, 1);
        } else if (fps >= 30) {
            // 30 or more FPS show up in yellow
            fpsFont.setColor(1, 1, 0, 1);
        } else {
            // less than 30 FPS show up in red
            fpsFont.setColor(1, 0, 0, 1);
        }
        fpsFont.draw(batch, "FPS: " + fps, x, y);
        fpsFont.setColor(1, 1, 1, 1); // white
    }

    @Override
    public void dispose() {

    }
}
