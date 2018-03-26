package com.packtpub.libgdx.canyonbunny.modle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.canyonbunny.utils.Assets;
import com.packtpub.libgdx.canyonbunny.utils.Constants;
import com.packtpub.libgdx.canyonbunny.utils.Logs;

/**
 * Created by user on 3/23/18.
 */

public class Rock extends AbstractGameObject {
    private static final String TAG = "=Rock";
    TextureRegion regEdge;
    TextureRegion regMiddle;
    float edgeWidth = 0.25f;
    float middleWidth = 1f;
    int length;

    public Rock(){
        init();
    }

    private void init() {
        dimension.set(middleWidth,2f);
        rotation = 0;
        regEdge = Assets.getInstance().findTextureByName(Constants.AtlasNames.ROCK_EDGE);
        Texture t= regEdge.getTexture();
        //Logs.d(TAG,"regEdge reg.getRegionX()="+regEdge.getRegionX());
        //Logs.d(TAG,"regEdge reg.getRegionY()="+regEdge.getRegionY());
        //Logs.d(TAG,"regEdge reg.getRegionWidth()="+regEdge.getRegionWidth());
        //Logs.d(TAG,"regEdge reg.getRegionHeight()="+regEdge.getRegionHeight());
        regMiddle = Assets.getInstance().findTextureByName(Constants.AtlasNames.ROCK_MIDDLE);
        length = 1;

    }

    public void setLength(int length) {
        this.length = length;
        float total = 0;
        if(length ==1){
            total = 1;
        }else if(length == 2){
            total = 1.25f;
        }else{
            total = length-2 + 0.5f;
        }
        this.bounds.set(0,0,total,dimension.y);
    }

    public void addLength(int amount){
        setLength(this.length + amount);
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion reg = null;
        float relX = 0;
        float relY = 0;

        // draw left rock
        reg = regEdge;
        relX -= dimension.x / 4;
        batch.draw(reg.getTexture(),
                position.x + relX, position.y + relY,
                origin.x, origin.y,
                dimension.x / 4, dimension.y,
                scale.x,scale.y,
                rotation,
                reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(),
                false, false);
        // Draw middle
        relX = 0;
        reg = regMiddle;
        for (int i = 0; i < length; i++) {
            batch.draw(reg.getTexture(), position.x + relX, position.y + relY,
                    origin.x, origin.y, dimension.x, dimension.y, scale.x,
                    scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
                    reg.getRegionWidth(), reg.getRegionHeight(), false, false);
            relX += dimension.x;
        }
        // Draw right edge
        reg = regEdge;
        batch.draw(reg.getTexture(),
                position.x + relX, position.y + relY,
                origin.x + dimension.x / 8, origin.y,
                dimension.x / 4,dimension.y,
                scale.x, scale.y,
                rotation,
                reg.getRegionX(),reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(),
                true, false);
    }


    public void showData(String tag){
        //Logs.d(TAG,tag+" rock = "+this.toString());
    }
}
