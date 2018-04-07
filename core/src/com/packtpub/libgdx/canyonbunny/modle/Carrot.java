package com.packtpub.libgdx.canyonbunny.modle;

/**
 * Created by anxi.xue on 2018/4/7.
 */

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.canyonbunny.utils.Assets;
import com.packtpub.libgdx.canyonbunny.utils.Constants;

public class Carrot extends AbstractGameObject {
    private TextureRegion regCarrot;

    public Carrot() {
        init();
    }

    private void init() {
        dimension.set(0.25f, 0.5f);
        regCarrot = Assets.instance.findTextureByName(Constants.AtlasNames.CARROT);
        // Set bounding box for collision detection
        bounds.set(0, 0, dimension.x, dimension.y);
        origin.set(dimension.x / 2, dimension.y / 2);
    }

    public void render(SpriteBatch batch) {
        TextureRegion reg = null;
        reg = regCarrot;
        batch.draw(reg.getTexture(), position.x - origin.x, position.y
                        - origin.y, origin.x, origin.y, dimension.x, dimension.y,
                scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(), false, false);
    }
}