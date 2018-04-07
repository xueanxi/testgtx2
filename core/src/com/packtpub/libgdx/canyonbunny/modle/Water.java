package com.packtpub.libgdx.canyonbunny.modle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.canyonbunny.utils.Assets;
import com.packtpub.libgdx.canyonbunny.utils.Constants;

/**
 * Created by anxi.xue on 2018/3/24.
 */

public class Water extends AbstractGameObject {
    TextureRegion regWater;
    float lenght;

    public Water(float lenght){
        this.lenght = lenght;
        init();

    }

    private void init() {
        dimension.set(lenght*10,3);
        regWater = Assets.instance.findTextureByName(Constants.AtlasNames.WATER_OVERLAY);
        origin.x = - dimension.x/2;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(regWater.getTexture(),position.x+origin.x,position.y+origin.y,
                origin.x,origin.y,
                dimension.x,dimension.y,
                scale.x,scale.y,
                rotation,regWater.getRegionX(),regWater.getRegionY(),
                regWater.getRegionWidth(),regWater.getRegionHeight(),
                false,false);
    }
}
