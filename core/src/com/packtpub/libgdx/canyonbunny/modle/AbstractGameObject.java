package com.packtpub.libgdx.canyonbunny.modle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by user on 3/23/18.
 */

public abstract class AbstractGameObject {
    protected Vector2 position;
    protected Vector2 dimension;
    protected Vector2 origin;
    protected Vector2 scale;
    protected float rotation;

    public AbstractGameObject(){
        position = new Vector2();
        dimension = new Vector2(1,1);
        origin = new Vector2();
        scale = new Vector2(1,1);
        rotation = 0f;
    }

    public void update(float deltaTime){

    }

    /**
     * 子类实例必须要实现 渲染方法
     * @param batch
     */
    public abstract void render(SpriteBatch batch);

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getDimension() {
        return dimension;
    }

    public void setDimension(Vector2 dimension) {
        this.dimension = dimension;
    }

    public Vector2 getScale() {
        return scale;
    }

    public void setScale(Vector2 scale) {
        this.scale = scale;
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public void setOrigin(Vector2 origin) {
        this.origin = origin;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public String toString() {
        return " {" +
                "position=" + position +
                ", dimension=" + dimension +
                ", origin=" + origin +
                ", scale=" + scale +
                ", rotation=" + rotation +
                '}';
    }
}
