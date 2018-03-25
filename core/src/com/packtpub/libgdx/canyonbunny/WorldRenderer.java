package com.packtpub.libgdx.canyonbunny;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    SpriteBatch batch;
    WorldController worldController;


    public WorldRenderer(WorldController worldController){
        this.worldController = worldController;
        init();



    }

    public void render(){
        worldController.cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        //renderTestObject();
        //renderRabit();

        //batch.draw(worldController.rabit,1,1,0,0,2,2,1,1,180);
        //batch.draw(worldController.rabit,0,0,0,0,2,2,1,1,0);
        //batch.draw(worldController.rabit,0,0,0,0,2,2,1,1,180);
        //batch.draw(worldController.rabit,0,0,0,0,2,2,1,1,180);
        //batch.draw(worldController.rabit,0,0,1,1,2,2,1,1,0);
        //batch.draw(worldController.feather,-2,-2,0,0,2,2,1,1,0);

        //worldController.rock.render(batch);
        //worldController.mountains.render(batch);
       // worldController.water.render(batch);
       // worldController.clouds.render(batch);
        worldController.level.render(batch);

        batch.end();
    }

    private void renderRabit() {
        if(worldController.mFeather != null){
            worldController.mFeather.draw(batch);
        }else{
            Logs.e(TAG,"mFeather is null!!!");
        }
    }

    private void renderTestObject() {


        for(Sprite sprite:worldController.testSprites){
            sprite.draw(batch);
        }

    }

    public void resize(int width, int height){

    }

    private void init(){
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        camera.position.set(0,0,0);
        camera.update();
    }

    @Override
    public void dispose() {

    }
}
