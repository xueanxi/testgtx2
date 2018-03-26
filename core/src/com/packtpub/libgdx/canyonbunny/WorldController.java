package com.packtpub.libgdx.canyonbunny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.canyonbunny.Level.Level;
import com.packtpub.libgdx.canyonbunny.modle.BuuyHead;
import com.packtpub.libgdx.canyonbunny.modle.Clouds;
import com.packtpub.libgdx.canyonbunny.modle.Feather;
import com.packtpub.libgdx.canyonbunny.modle.GlodCoin;
import com.packtpub.libgdx.canyonbunny.modle.Mountains;
import com.packtpub.libgdx.canyonbunny.modle.Rock;
import com.packtpub.libgdx.canyonbunny.modle.Water;
import com.packtpub.libgdx.canyonbunny.utils.Assets;
import com.packtpub.libgdx.canyonbunny.utils.Constants;
import com.packtpub.libgdx.canyonbunny.utils.Logs;

/**
 * Created by user on 3/22/18.
 */

public class WorldController extends InputAdapter {
    private static final String TAG = "=WorldController";
    public Sprite[] testSprites;
    public int selectedSprite = 0;
    public CameraHelper cameraHelper;
    public Assets mAssets;

    public Level level;
    public int score = 0;
    public int lives = 1;

    private Rectangle r1;
    private Rectangle r2;


    public WorldController(){
        init();
    }
    public void init(){
        Gdx.input.setInputProcessor(this);
        mAssets = Assets.getInstance();

        level = new Level(Constants.Level.LEVEL_01);
        cameraHelper = new CameraHelper();
        cameraHelper.setTarget(level.buuyhead);
    }


    boolean isLog = true;
    public void testCollistion(){
        r1 = new Rectangle();
        r2 = new Rectangle();

        r1.set(level.buuyhead.position.x,level.buuyhead.position.y,
                level.buuyhead.bounds.width,level.buuyhead.bounds.height);

        Rock rock = null;
        GlodCoin coin = null;
        Feather feather = null;
        for(int i = 0;i<level.rocks.size;i++){
            rock = level.rocks.get(i);
            r2.set(rock.position.x,rock.position.y,rock.bounds.width,rock.bounds.height);
            if(!r1.overlaps(r2)){
                if(isLog)Logs.d(TAG,"No collision r1:"+r1+"  r2:"+r2);
                continue;
            }else{
                onCollisionBunnyHeadWithRock(rock);
            }
        }
        isLog = false;

        for(int i = 0;i<level.goldcoins.size;i++){
            coin = level.goldcoins.get(i);
            r2.set(coin.position.x,coin.position.y,coin.bounds.width,coin.bounds.height);
            if(!r1.overlaps(r2)){
                continue;
            }else{
                onCollisionBunnyWithGoldCoin(coin);
            }
        }

        for(int i = 0;i<level.feathers.size;i++){
            feather = level.feathers.get(i);
            r2.set(feather.position.x,feather.position.y,feather.bounds.width,feather.bounds.height);
            if(!r1.overlaps(r2)){
                continue;
            }else{
                onCollisionBunnyWithFeather(feather);
            }
        }

    }

    private void onCollisionBunnyHeadWithRock(Rock rock) {
        Logs.d(TAG,"onCollisionBunnyHeadWithRock");
        BuuyHead bunnyHead = level.buuyhead;
        float heightDifference = Math.abs(bunnyHead.position.y - (rock.position.y + rock.bounds.height));
        if (heightDifference > 0.25f) {
            boolean hitLeftEdge = bunnyHead.position.x > (rock.position.x + rock.bounds.width / 2.0f);
            if (hitLeftEdge) {
                bunnyHead.position.x = rock.position.x + rock.bounds.width;
            } else {
                bunnyHead.position.x = rock.position.x - bunnyHead.bounds.width;
            }
            return;
        }
        switch (bunnyHead.jumpState) {
            case GROUNDED:
                break;
            case FALLING:
            case JUMP_FALLING:
                bunnyHead.position.y = rock.position.y + bunnyHead.bounds.height
                        + bunnyHead.origin.y;
                bunnyHead.jumpState = BuuyHead.JUMP_STATE.GROUNDED;
                break;
            case JUMP_RISING:
                bunnyHead.position.y = rock.position.y + bunnyHead.bounds.height
                        + bunnyHead.origin.y;
                break;
        }
    }

    private void onCollisionBunnyWithGoldCoin(GlodCoin goldcoin) {
        goldcoin.setCollect(true);
        score += goldcoin.getScore();
        Gdx.app.log(TAG, "Gold coin collected");
    }

    private void onCollisionBunnyWithFeather(Feather feather) {
        feather.setCollect(true);
        score += feather.getScore();
        level.buuyhead.setFeatherPowerup(true);
        Gdx.app.log(TAG, "Feather collected");
    }

    private void initImgObject() {
        testSprites = new Sprite[5];
        int width = 32;
        int height = 32;
        Array<TextureRegion> textureArray = new Array<TextureRegion>(3);
        textureArray.add(mAssets.findTextureByName(Constants.AtlasNames.BUNNY_HEAD));
        textureArray.add(mAssets.findTextureByName(Constants.AtlasNames.MOUNTAIN_LEFT));
        textureArray.add(mAssets.findTextureByName(Constants.AtlasNames.MOUNTAIN_RIGHT));
        textureArray.add(mAssets.findTextureByName(Constants.AtlasNames.ROCK_EDGE));
        textureArray.add(mAssets.findTextureByName(Constants.AtlasNames.ROCK_MIDDLE));

        for (int i = 0; i < testSprites.length; i++) {
            Sprite spr = new Sprite(textureArray.get(i));
            // Define sprite size to be 1m x 1m in game world
            spr.setSize(1, 1);
            // Set origin to sprite's center
            spr.setOrigin(spr.getWidth() / 2.0f, spr.getHeight() / 2.0f);
            // Calculate random position for sprite
            float randomX = MathUtils.random(-2.0f, 2.0f);
            float randomY = MathUtils.random(-2.0f, 2.0f);
            spr.setPosition(randomX, randomY);
            // Put new sprite into array
            testSprites[i] = spr;
        }
        // Set first sprite as selected one
        selectedSprite = 0;
    }

    private void initTestObject() {
        // Create new array for 5 sprites
        testSprites = new Sprite[5];
        // Create empty POT-sized Pixmap with 8 bit RGBA pixel data
        int width = 32;
        int height = 32;
        Pixmap pixmap = createProceduralPixmap(width, height);
        // Create a new texture from pixmap data
        Texture texture = new Texture(pixmap);
        // Create new sprites using the just created texture
        for (int i = 0; i < testSprites.length; i++) {
            Sprite spr = new Sprite(texture);
            // Define sprite size to be 1m x 1m in game world
            spr.setSize(1, 1);
            // Set origin to sprite's center
            spr.setOrigin(spr.getWidth() / 2.0f, spr.getHeight() / 2.0f);
            // Calculate random position for sprite
            float randomX = MathUtils.random(-2.0f, 2.0f);
            float randomY = MathUtils.random(-2.0f, 2.0f);
            spr.setPosition(randomX, randomY);
            // Put new sprite into array
            testSprites[i] = spr;
        }
        // Set first sprite as selected one
        selectedSprite = 0;
    }

    private Pixmap createProceduralPixmap(int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        // Fill square with red color at 50% opacity
        pixmap.setColor(1, 0, 0, 0.5f);
        pixmap.fill();
        // Draw a yellow-colored X shape on square
        pixmap.setColor(1, 1, 0, 1);
        pixmap.drawLine(0, 0, width, height);
        pixmap.drawLine(width, 0, 0, height);
        // Draw a cyan-colored border around square
        pixmap.setColor(0, 1, 1, 1);
        pixmap.drawRectangle(0, 0, width, height);
        return pixmap;
    }

    private void updateTestObjects(float deltaTime) {
        // Get current rotation from selected sprite
        float rotation = testSprites[selectedSprite].getRotation();
        // Rotate sprite by 90 degrees per second
        rotation += 90 * deltaTime;
        //Logs.d(TAG,"deltaTime = "+deltaTime+" rotation="+rotation);
        // Wrap around at 360 degrees
        rotation %= 360;
        // Set new rotation value to selected sprite
        testSprites[selectedSprite].setRotation(rotation);
    }

    private void rotationObject(float deltaTime,Sprite sprite,boolean isClock) {
        // Get current rotation from selected sprite
        float rotation = testSprites[selectedSprite].getRotation();
        // Rotate sprite by 90 degrees per second
        if(isClock){
            rotation += 90 * deltaTime;
        }else{
            rotation -= 90 * deltaTime;
        }

        //Logs.d(TAG,"deltaTime = "+deltaTime+" rotation="+rotation);
        // Wrap around at 360 degrees
        rotation %= 360;
        // Set new rotation value to selected sprite
        sprite.setRotation(rotation);
    }

    public void update(float deltaTime){
        //updateTestObjects(deltaTime);
        handleInputGame(deltaTime);
        //handleDebugInput(deltaTime);
        level.update(deltaTime);
        testCollistion();
        cameraHelper.update(deltaTime);
    }

    private void handleInputGame(float deltaTime) {
        //if (cameraHelper.hasTarget(level.buuyhead)) {
            // Player Movement
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                level.buuyhead.velocity.x = -level.buuyhead.terminalVelocity.x;
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                level.buuyhead.velocity.x = level.buuyhead.terminalVelocity.x;
            } else {
                // Execute auto-forward movement on non-desktop platform
                if (Gdx.app.getType() != Application.ApplicationType.Desktop) {
                    level.buuyhead.velocity.x = level.buuyhead.terminalVelocity.x;
                }
            }
            // Bunny Jump
            if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.SPACE))
                level.buuyhead.setJumping(true);
            else
                level.buuyhead.setJumping(false);
        //}
    }

    private void handleDebugInput(float deltaTime) {
        if(Gdx.app.getType() != Application.ApplicationType.Desktop){
            return;
        }

        // 控制箱子移动
        float sprMoveSpeed = deltaTime *5;
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            moveSelectedSprite(-sprMoveSpeed,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            moveSelectedSprite(0,-sprMoveSpeed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            moveSelectedSprite(sprMoveSpeed,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            moveSelectedSprite(0,sprMoveSpeed);
        }

        //控制摄像机移动
        float camMoveSpeed = 5 * deltaTime;
        float camMoveSpeedAccelerationFactor = 5;
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            camMoveSpeed *= camMoveSpeedAccelerationFactor;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            moveCamera(-camMoveSpeed,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            moveCamera(0,-camMoveSpeed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            moveCamera(+camMoveSpeed,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            moveCamera(0,camMoveSpeed);
        }

        // 控制摄像机的缩放
        float camZoomSpeed = 1 * deltaTime;
        float camZoomSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            camZoomSpeed *= camZoomSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Input.Keys.Z)){
            cameraHelper.addZoom(camZoomSpeed);
            Logs.d(TAG,"Z");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.X)){
            cameraHelper.addZoom(-camZoomSpeed);
            Logs.d(TAG,"X");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.C)){
            cameraHelper.setZoom(1);
        }

        // 旋转精灵
    }

    private void moveCamera(float moveX,float moveY){
        float x = cameraHelper.getPosition().x;
        float y = cameraHelper.getPosition().y;
        x+=moveX;
        y+=moveY;
        cameraHelper.setPosition(x,y);
    }

    private void moveSelectedSprite(float x,float y){
        if(level.buuyhead == null) return;
        level.buuyhead.setPosition(new Vector2(x,y));
    }

    @Override
    public boolean keyUp(int keycode) {
        if(Input.Keys.R == keycode){
            init();
            Logs.d(TAG,"Game is reset!!!");
        }else if (keycode == Input.Keys.ENTER) {
            cameraHelper.setTarget(cameraHelper.hasTarget() ? null
                    : level.buuyhead);
            Gdx.app.debug(TAG,
                    "Camera follow enabled: " + cameraHelper.hasTarget());
        }
        return false;
    }
}
