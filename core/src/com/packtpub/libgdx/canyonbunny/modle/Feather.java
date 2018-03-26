package com.packtpub.libgdx.canyonbunny.modle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.canyonbunny.utils.Assets;
import com.packtpub.libgdx.canyonbunny.utils.Constants;

/**
 * Created by user on 3/26/18.
 */

public class Feather extends AbstractGameObject {

    boolean isCollect = false;
    TextureRegion regCoin;

    public Feather(){
        init();
    }

    private void init() {
        dimension.set(0.5f,0.5f);
        regCoin = Assets.getInstance().findTextureByName(Constants.AtlasNames.ITEM_FEATHER);
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
        return 200;
    }

    public boolean isCollect(){
        return isCollect;
    }

    public void setCollect(boolean isCollect){
        this.isCollect = isCollect;
    }
}
