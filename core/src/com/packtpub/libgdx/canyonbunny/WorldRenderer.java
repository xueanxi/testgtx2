package com.packtpub.libgdx.canyonbunny;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.packtpub.libgdx.canyonbunny.utils.Assets;
import com.packtpub.libgdx.canyonbunny.utils.Constants;
import com.packtpub.libgdx.canyonbunny.utils.Logs;

/**
 * Created by user on 3/22/18.
 */

public class WorldRenderer implements Disposable {
    private static final String TAG = "=WorldRenderer";

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
        batch.begin();
        // draw collected gold coins icon + text
        // (anchored to top left edge)
        renderGuiScore(batch);
        // draw extra lives icon + text (anchored to top right edge)
        renderGuiExtraLive(batch);
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
    }

    private void renderGuiScore(SpriteBatch batch) {

        TextureRegion reg = Assets.getInstance().findTextureByName(Constants.AtlasNames.ITEM_GOLD_COIN);
        int width = reg.getRegionWidth();
        int height = reg.getRegionHeight();
        float x = 15;
        float y = cameraGUI.viewportHeight - height -5;
        batch.draw(reg,
                x, y,
                width/2, height/2,
                width, height,
                0.6f, 0.6f, 0);
        Assets.getInstance().getBitmapFont(2).draw(batch,"" + worldController.score, x + width, y + height*0.5f);
    }

    private void renderGuiExtraLive(SpriteBatch batch) {
        float x = cameraGUI.viewportWidth  - Constants.LIVES_START * 50;
        float y = cameraGUI.viewportHeight - 50;
        TextureRegion reg = Assets.getInstance().findTextureByName(Constants.AtlasNames.BUNNY_HEAD);
        float width = 40;
        float height = 40 * reg.getRegionWidth()/reg.getRegionWidth();
        for (int i = 0; i < Constants.LIVES_START; i++) {
            if (worldController.lives <= i){
                batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
            }
            batch.draw(reg,
                    x + i * 50, y,//(x,y)
                    width/2, height/2,//(orig.x,orig.y)
                    width,height,//(width,height)
                    1, 1, 0);
            batch.setColor(1, 1, 1, 1);
        }
    }

    private void renderGuiGameOverMessage(SpriteBatch batch) {
        if (worldController.isGameOver()) {
            float x = cameraGUI.viewportWidth / 2;
            float y = cameraGUI.viewportHeight / 2;
            BitmapFont fontGameOver = Assets.getInstance().getBitmapFont(3);
            fontGameOver.setColor(1, 0.75f, 0.25f, 1);
            fontGameOver.draw(batch, "GAME OVER", x, y,100, Align.center,true);
            fontGameOver.setColor(1, 1, 1, 1);
        }
    }

    private void renderGuiFpsCounter(SpriteBatch batch) {
        float x = cameraGUI.viewportWidth - 55;
        float y =  15;
        int fps = Gdx.graphics.getFramesPerSecond();
        BitmapFont fpsFont = Assets.getInstance().getBitmapFont(2);
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
