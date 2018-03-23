package com.packtpub.libgdx.canyonbunny.modle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.canyonbunny.utils.Assets;
import com.packtpub.libgdx.canyonbunny.utils.Constants;

/**
 * Created by user on 3/23/18.
 */

public class Rock extends AbstractGameObject {

    TextureRegion regLeftEdge;
    TextureRegion regEdge;
    TextureRegion regRightEdge;
    TextureRegion regMiddle;
    int length;

    public Rock(){
        init();
    }

    private void init() {
        dimension.set(1,1.5f);
        regEdge = Assets.getInstance().findTextureByName(Constants.AtlasNames.ROCK_EDGE);
        regMiddle = Assets.getInstance().findTextureByName(Constants.AtlasNames.ROCK_MIDDLE);
        length = 1;
    }

    public void setLength(int length) {
        this.length = length;
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
        batch.draw(reg.getTexture(), position.x + relX, position.y + relY,
                origin.x, origin.y, dimension.x / 4, dimension.y, scale.x,
                scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(), false, false);
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
        batch.draw(reg.getTexture(), position.x + relX, position.y + relY,
                origin.x + dimension.x / 8, origin.y, dimension.x / 4,
                dimension.y, scale.x, scale.y, rotation, reg.getRegionX(),
                reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
                true, false);
    }


}
