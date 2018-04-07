package com.packtpub.libgdx.canyonbunny.modle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.canyonbunny.utils.Assets;
import com.packtpub.libgdx.canyonbunny.utils.Constants;

/**
 * Created by user on 3/26/18.
 */

public class GlodCoin extends AbstractGameObject {

    boolean isCollect = false;
    TextureRegion regCoin;

    public GlodCoin(){
        init();
    }

    private void init() {
        dimension.set(0.5f,0.5f);
        regCoin = Assets.instance.findTextureByName(Constants.AtlasNames.ITEM_GOLD_COIN);
        bounds.set(0,0,dimension.x,dimension.y);
        isCollect = false;
    }


    @Override
    public void render(SpriteBatch batch) {
        if(isCollect)return;
        batch.draw(regCoin.getTexture(),position.x,position.y,
                origin.x,origin.y,
                dimension.x,dimension.y,
                scale.x,scale.y,
                rotation,
                regCoin.getRegionX(),regCoin.getRegionY(),
                regCoin.getRegionWidth(),regCoin.getRegionHeight(),
                false,false
                );
    }

    public int getScore(){
        return 100;
    }

    public boolean isCollect(){
        return isCollect;
    }

    public void setCollect(boolean isCollect){
        this.isCollect = isCollect;
    }
}
