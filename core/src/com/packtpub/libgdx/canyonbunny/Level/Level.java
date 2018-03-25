package com.packtpub.libgdx.canyonbunny.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.canyonbunny.modle.AbstractGameObject;
import com.packtpub.libgdx.canyonbunny.modle.Clouds;
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

    Mountains mountains;
    Clouds clouds;
    Water water;
    Array<Rock> rocks;
    String fileName;

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
        int currentColor;
        for(int y = 0; y< pixmap.getHeight(); y++){
            for(int x=0;x<pixmap.getWidth();x++){
                currentPosition = new Vector2(x,y);
                currentColor = pixmap.getPixel(x,y);
                if(currentColor == BLOCK_TYPE.EMTY.getColor()){
                    // 空白区域
                }else if(currentColor == BLOCK_TYPE.ROCK.getColor()){
                    // 岩石区域
                }else if(currentColor == BLOCK_TYPE.PLAYER.getColor()){
                    // 玩家初始的位置

                }else if(currentColor == BLOCK_TYPE.FEATHER.getColor()){
                    // 羽毛
                }else if(currentColor == BLOCK_TYPE.GLOD.getColor()){
                    // 金币
                }else{
                    Logs.e(TAG,"Error color when init Level for "+fileName);
                }
            }
        }
    }

    Pixmap pixmap;
    public void render(SpriteBatch batch) {
        batch.draw(new Texture(pixmap),0,0);
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
