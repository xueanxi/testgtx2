package com.packtpub.libgdx.canyonbunny.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.canyonbunny.modle.AbstractGameObject;
import com.packtpub.libgdx.canyonbunny.modle.BuuyHead;
import com.packtpub.libgdx.canyonbunny.modle.Clouds;
import com.packtpub.libgdx.canyonbunny.modle.Feather;
import com.packtpub.libgdx.canyonbunny.modle.GlodCoin;
import com.packtpub.libgdx.canyonbunny.modle.Mountains;
import com.packtpub.libgdx.canyonbunny.modle.Rock;
import com.packtpub.libgdx.canyonbunny.modle.Water;
import com.packtpub.libgdx.canyonbunny.utils.Logs;

/**
 * 游戏场景类
 * Created by anxi.xue on 2018/3/25.
 */

public class Level {
    private static final String TAG = "=Level";

    public Mountains mountains;
    public Clouds clouds;
    public Water water;
    public Array<Rock> rocks;
    String fileName;
    public BuuyHead buuyhead;
    public Array<GlodCoin> goldcoins;
    public Array<Feather> feathers;

    public Level(String fileName) {
        init(fileName);
    }

    /**
     * 根据文件初始化地图
     * @param fileName
     */
    private void init(String fileName) {
        pixmap = new Pixmap(Gdx.files.internal(fileName));
        Vector2 currentPosition = null;
        AbstractGameObject obj = null;
        rocks = new Array<Rock>();
        goldcoins = new Array<GlodCoin>();
        feathers = new Array<Feather>();
        int lastPixel = -1;
        int currentPixel = -1;
        float heightIncreaseFactor = 0.25f;
        for(int y = 0; y< pixmap.getHeight(); y++){
            for(int x=0;x<pixmap.getWidth();x++){
                currentPixel = pixmap.getPixel(x,y);
                // y的取值是从上到下的，这样最高一层y=0,与我们现实不对，我们应该最低一层为0，所以处理一下。
                float baseHeight = pixmap.getHeight() - y;
                float offsetHeight = 0;// TODO: 3/26/18 这个变量是干嘛用的
                obj = null;
                if(currentPixel == BLOCK_TYPE.EMTY.getColor()){
                    // 空白区域
                }else if(currentPixel == BLOCK_TYPE.ROCK.getColor()){
                    // 岩石区域
                    if(currentPixel == lastPixel){
                        rocks.get(rocks.size-1).addLength(1);
                    }else{
                        obj = new Rock();
                        offsetHeight = -2.5f;
                        obj.position.set(x, baseHeight * obj.dimension.y * heightIncreaseFactor + offsetHeight);
                        rocks.add((Rock)obj);
                    }
                }else if(currentPixel == BLOCK_TYPE.PLAYER.getColor()){
                    // 玩家初始的位置
                    obj = new BuuyHead();
                    offsetHeight = -1.5f;
                    obj.position.set(x+2,baseHeight * obj.dimension.y+ offsetHeight);
                    buuyhead = (BuuyHead)obj;
                }else if(currentPixel == BLOCK_TYPE.FEATHER.getColor()){
                    // 羽毛
                    offsetHeight = -1.5f;
                    obj = new Feather();
                    obj.setPosition(new Vector2(x,baseHeight * obj.dimension.y+offsetHeight));
                    feathers.add((Feather) obj);
                }else if(currentPixel == BLOCK_TYPE.GLOD.getColor()){
                    // 金币
                    offsetHeight = -1.5f;
                    obj = new GlodCoin();
                    obj.setPosition(new Vector2(x,baseHeight * obj.dimension.y+offsetHeight));
                    goldcoins.add((GlodCoin) obj);
                }else{
                    Logs.e(TAG,"Error color when init Level for "+fileName);
                }
                lastPixel = currentPixel;
            }
        }

        clouds = new Clouds(pixmap.getWidth(),5);
        clouds.setPosition(new Vector2(0f,2f));
        mountains = new Mountains(pixmap.getWidth());
        mountains.setPosition(new Vector2(-1,-1));
        water = new Water(pixmap.getWidth());
        water.setPosition(new Vector2(0,-3.75f));
        pixmap.dispose();
        Logs.d(TAG,"init map finish!");
    }

    Pixmap pixmap;
    public void render(SpriteBatch batch) {
        mountains.render(batch);
        // Draw Rocks
        for (Rock rock : rocks)
            rock.render(batch);
        // Draw Gold Coins
        for (GlodCoin goldCoin : goldcoins)
            goldCoin.render(batch);
        // Draw Feathers
        for (Feather feather : feathers)
            feather.render(batch);
        // Draw Player Character
        buuyhead.render(batch);

        // Draw Water Overlay
        water.render(batch);
        // Draw Clouds
        clouds.render(batch);
    }

    public void update (float deltaTime) {
        buuyhead.update(deltaTime);
        for(Rock rock : rocks)
            rock.update(deltaTime);
        for(GlodCoin goldCoin : goldcoins)
            goldCoin.update(deltaTime);
        for(Feather feather : feathers)
            feather.update(deltaTime);
        clouds.update(deltaTime);
    }

    // 枚举类型
    public enum BLOCK_TYPE {
        EMTY(0,0,0),//黑色
        ROCK(0,255,0),//绿色
        PLAYER(255,255,255),//白色
        FEATHER(255,0,255),//紫色
        GLOD(255,255,0); //黄色

        private int color;

        private BLOCK_TYPE(int r, int g, int b) {
            color = r << 24 | g << 16 | b << 8 | 0xff;
        }

        public boolean isSameColor(int color){
            return this.color == color;
        }

        public int getColor(){
            return this.color;
        }
    }
}
