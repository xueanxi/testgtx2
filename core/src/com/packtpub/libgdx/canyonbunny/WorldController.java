package com.packtpub.libgdx.canyonbunny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.packtpub.libgdx.canyonbunny.Level.Level;
import com.packtpub.libgdx.canyonbunny.game.MenuScreen;
import com.packtpub.libgdx.canyonbunny.modle.BuuyHead;
import com.packtpub.libgdx.canyonbunny.modle.Feather;
import com.packtpub.libgdx.canyonbunny.modle.GlodCoin;
import com.packtpub.libgdx.canyonbunny.modle.Rock;
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
    public int score;
    public int lives;
    private Rectangle r1;
    private Rectangle r2;

    public Game game;

    private float timeLeftGameOverDelay;// 用于统计游戏中玩家挂掉之后的时间，用于判断3秒后复活玩家


    public WorldController(Game game){
        this.game = game;
        init();
    }

    public void init(){
        Gdx.input.setInputProcessor(this);
        mAssets = Assets.getInstance();
        timeLeftGameOverDelay = 0;
        lives = Constants.LIVES_START;
        score = 0;
        level = new Level(Constants.Level.LEVEL_01);
        cameraHelper = new CameraHelper();
        cameraHelper.setTarget(level.buuyhead);
    }

    public void backToMenu(){
        // switch to menu screen
        game.setScreen(new MenuScreen(game));
    }

    /**
     * 如果玩家还有生命值，在挂掉之后可以复活
     */
    private void revive(){
        level.buuyhead.setPosition(new Vector2(level.startPosition));
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
        //Logs.d(TAG,"onCollisionBunnyHeadWithRock");
        BuuyHead bunnyHead = level.buuyhead;
        float heightDifference = Math.abs(bunnyHead.position.y - (rock.position.y + rock.bounds.height));
        if (heightDifference > 0.25f) {
            Rectangle bunnyBounds = bunnyHead.bounds;
            Rectangle rockBounds = rock.bounds;
            float bunnyHeadX;
            // 如果从左边撞进岩石，应该返回原来的地方，因为不能进入岩石。
            if ((bunnyBounds.x+bunnyBounds.width > rockBounds.x) && (bunnyBounds.x+bunnyBounds.width < rockBounds.x+rockBounds.width)) {
                bunnyHeadX = rock.position.x - bunnyBounds.width;
                bunnyHead.setPosition(new Vector2(bunnyHeadX,bunnyBounds.y));
            }
            // 如果从右边撞进岩石，应该返回原来的地方，因为不能进入岩石。
            else if(bunnyHead.viewDirection == BuuyHead.VIEW_DIRECTION.LEFT &&  bunnyBounds.x < rockBounds.x+rockBounds.width){
                bunnyHeadX = rock.position.x + rock.dimension.x;
                bunnyHead.setPosition(new Vector2(bunnyHeadX,bunnyBounds.y));
            }
            return;
        }
        switch (bunnyHead.jumpState) {
            case GROUNDED:
                break;
            case FALLING:
            case JUMP_FALLING:
                float y = rock.bounds.getY() + rock.bounds.getHeight();
                bunnyHead.setPosition(new Vector2(bunnyHead.position.x,y));
                bunnyHead.jumpState = BuuyHead.JUMP_STATE.GROUNDED;
                break;
            case JUMP_RISING:
                bunnyHead.position.y = rock.position.y + bunnyHead.bounds.height
                        + bunnyHead.origin.y;
                break;
        }
    }

    private void onCollisionBunnyWithGoldCoin(GlodCoin goldcoin) {
        if(!goldcoin.isCollect()){
            goldcoin.setCollect(true);
            score += goldcoin.getScore();
            Gdx.app.log(TAG, "Gold coin collected");
        }
    }

    private void onCollisionBunnyWithFeather(Feather feather) {
        feather.setCollect(true);
        score += feather.getScore();
        level.buuyhead.setFeatherPowerup(true);
        Gdx.app.log(TAG, "Feather collected");
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

    public void update(float deltaTime){

        handleDebugInput(deltaTime);

        if(isGameOver()){
            timeLeftGameOverDelay += deltaTime;
            if(timeLeftGameOverDelay > Constants.TIME_DELAY_GAME_OVER){
                timeLeftGameOverDelay = 0;
                init();
            }
        }else{
            handleInputGame(deltaTime);
            level.update(deltaTime);
            testCollistion();
            cameraHelper.update(deltaTime);
            if (isPlayerInWater()) {
                lives--;
                if (!isGameOver()){
                    revive();
                }
            }
        }
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
        //控制摄像机移动
        float camMoveSpeed = 5 * deltaTime;
        float camMoveSpeedAccelerationFactor = 5;
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            camMoveSpeed *= camMoveSpeedAccelerationFactor;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            moveCamera(-camMoveSpeed,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            moveCamera(0,-camMoveSpeed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            moveCamera(+camMoveSpeed,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            moveCamera(0,camMoveSpeed);
        }

        // 控制摄像机的缩放
        float camZoomSpeed = 1 * deltaTime;
        float camZoomSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            camZoomSpeed *= camZoomSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Input.Keys.Z)){
            cameraHelper.addZoom(camZoomSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.X)){
            cameraHelper.addZoom(-camZoomSpeed);
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
            Gdx.app.debug(TAG,"Camera follow enabled: " + cameraHelper.hasTarget());
        }
        return false;
    }

    public boolean isGameOver(){
        if(lives <= 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean isPlayerInWater(){
        if(level.buuyhead.getPosition().y < -4){
            return true;
        }else{
            return false;
        }
    }
}
