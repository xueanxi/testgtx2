package com.packtpub.libgdx.canyonbunny.modle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
    float edgeWidth = 0.2f;
    float middleWidth = 1f;
    int length;

    public Rock(){
        init();
    }

    private void init() {
        dimension.set(middleWidth,2f);
        rotation = 0;
        regEdge = Assets.getInstance().findTextureByName(Constants.AtlasNames.ROCK_EDGE);
        regMiddle = Assets.getInstance().findTextureByName(Constants.AtlasNames.ROCK_MIDDLE);
        length = 1;
    }

    public void setLength(int length) {
        this.length = length;
        setDimension(new Vector2(length * middleWidth,dimension.y));
    }

    public void addLength(int amount){
        setLength(this.length + amount);
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion reg = null;
        float relX = 0;

        // draw left rock
        reg = regEdge;
        relX -= edgeWidth;
        batch.draw(reg.getTexture(),
                position.x + relX, position.y,
                origin.x+0.1f, origin.y,
                edgeWidth, dimension.y,
                scale.x,scale.y,
                rotation,
                reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(),
                false, false);
        // Draw middle
        relX = 0;
        reg = regMiddle;
        for (int i = 0; i < length; i++) {
            batch.draw(
                    reg.getTexture(), position.x + relX, position.y,
                    origin.x, origin.y, middleWidth, dimension.y, scale.x,
                    scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
                    reg.getRegionWidth(), reg.getRegionHeight(), false, false);
            relX += 1;
        }
        // Draw right edge
        reg = regEdge;
        batch.draw(reg.getTexture(),
                position.x + relX, position.y ,
                origin.x + 0.1f, origin.y,
                edgeWidth,dimension.y,
                scale.x, scale.y,
                rotation,
                reg.getRegionX(),reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(),
                true, false);
    }
}
