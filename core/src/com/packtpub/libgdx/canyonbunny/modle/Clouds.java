package com.packtpub.libgdx.canyonbunny.modle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.canyonbunny.utils.Assets;
import com.packtpub.libgdx.canyonbunny.utils.Constants;
import com.packtpub.libgdx.canyonbunny.utils.Logs;

/**
 * Created by anxi.xue on 2018/3/24.
 */

public class Clouds extends AbstractGameObject {
    Array<Cloud> clouds;
    Array<TextureRegion> regArrayCloud;
    float length;// 云的总长度，也可以看成地图横长度
    float distFac;// 一朵云的大小 + 两朵云之家的距离

    public  Clouds(float length,float disFac){
        this.length = length;
        this.distFac = disFac;
        init();
    }

    private void init() {
        dimension.set(3,1.5f);
        regArrayCloud = new Array<TextureRegion>(3);
        clouds = new Array<Cloud>();
        regArrayCloud.add(Assets.instance.findTextureByName(Constants.AtlasNames.CLOUD01));
        regArrayCloud.add(Assets.instance.findTextureByName(Constants.AtlasNames.CLOUD02));
        regArrayCloud.add(Assets.instance.findTextureByName(Constants.AtlasNames.CLOUD03));
        int cloudNum = (int) (length/distFac);
        Cloud cloud = null;
        for(int i = 0;i<cloudNum;i++){
            cloud = createCloud();
            cloud.position.x = i*distFac;
            clouds.add(cloud);
        }
    }

    private Cloud createCloud(){
        Cloud cloud = new Cloud(regArrayCloud.random());
        cloud.setDimension(this.dimension);
        Vector2 postion = new Vector2();
        postion.x = length+10;
        postion.y = 1.7f;
        postion.y += MathUtils.random(0,0.2f)* (MathUtils.randomBoolean()?1:-1);
        cloud.setPosition(postion);


        // speed
        Vector2 speed = new Vector2();
        speed.x += 0.3f; // base speed
        // random additional speed
        speed.x += MathUtils.random(0.0f, 0.35f);
        cloud.terminalVelocity.set(speed);
        speed.x *= -1; // move left
        cloud.velocity.set(speed);
        cloud.friction.set(0,0);
        return cloud;
    }

    @Override
    public void render(SpriteBatch batch) {
        if(clouds != null &&clouds.size >= 0){
            for(Cloud cloud :clouds){
                cloud.render(batch);
            }
        }
    }


    @Override
    public void update(float deltaTime){
        for(Cloud c:clouds){
            c.update(deltaTime);
        }
    }


    public class Cloud extends AbstractGameObject{
        private static final String TAG = "=Cloud";
        TextureRegion regCloud;

        public Cloud(TextureRegion reg){
            this.regCloud = reg;
        }

        @Override
        public void render(SpriteBatch batch) {
            batch.draw(regCloud.getTexture(),position.x,position.y,
                    origin.x,origin.y,
                    dimension.x,dimension.y,
                    scale.x,scale.y,
                    rotation,
                    regCloud.getRegionX(),regCloud.getRegionY(),
                    regCloud.getRegionWidth(),regCloud.getRegionHeight(),
                    false,false);
        }

        @Override
        public void update(float deltaTime){
            updateMotionX(deltaTime);
            updateMotionY(deltaTime);
            position.x += velocity.x * deltaTime;
            position.y += velocity.y * deltaTime;
            //bounds.set(position.x,position.y,dimension.x,dimension.y);
        }
    }
}
