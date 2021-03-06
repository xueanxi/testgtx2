package com.packtpub.libgdx.canyonbunny.modle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by user on 3/23/18.
 */

public abstract class AbstractGameObject {
    public Vector2 position;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 scale;
    public float rotation;

    //物理特性
    public Vector2 velocity;
    public Vector2 terminalVelocity;
    public Vector2 friction;
    public Vector2 acceleration;
    public Rectangle bounds;

    public Body body;

    public AbstractGameObject(){
        position = new Vector2();
        dimension = new Vector2(1,1);
        origin = new Vector2();
        scale = new Vector2(1,1);
        rotation = 0f;
        bounds =new Rectangle(0,0,0,0);
        terminalVelocity = new Vector2(0,0);
        friction = new Vector2(0,0);
        velocity = new Vector2(0,0);
        acceleration = new Vector2(0,0);
    }

    public void update(float deltaTime){
        if(body == null){
            updateMotionX(deltaTime);
            updateMotionY(deltaTime);
            position.x += velocity.x * deltaTime;
            position.y += velocity.y * deltaTime;
            bounds.set(position.x,position.y,dimension.x,dimension.y);
        }else{
            setPosition(body.getPosition());
            rotation = body.getAngle() * MathUtils.radiansToDegrees;
        }
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
        updateBounds();
    }


    public Vector2 getDimension() {
        return dimension;
    }

    public void setDimension(Vector2 dimension) {
        this.dimension = dimension;
        updateBounds();
    }

    protected void updateBounds(){
        this.bounds.set(this.position.x,this.position.y,this.dimension.x,this.dimension.y);
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

    protected void updateMotionX(float deltaTime){
        //处理摩擦力
        if(this.velocity.x>0){
            // 如果速度是正方向的，摩擦力会使正方向的速度变小
            velocity.x = Math.max(velocity.x - friction.x * deltaTime,0);
        }else{
            // 如果速度是负方向的，摩擦力会使负方向的速度变小
            velocity.x = Math.min(velocity.x + friction.x * deltaTime, 0);
        }

        //处理加速度
        velocity.x += acceleration.x * deltaTime;
        //加速之后的速度，不能超出极限速度
        velocity.x = MathUtils.clamp(velocity.x,-terminalVelocity.x,terminalVelocity.x);
    }

    protected void updateMotionY(float deltaTime){
        if (velocity.y != 0) {
            // Apply friction
            if (velocity.y > 0) {
                velocity.y = Math.max(velocity.y - friction.y * deltaTime, 0);
            } else {
                velocity.y = Math.min(velocity.y + friction.y * deltaTime, 0);
            }
        }
        // Apply acceleration
        velocity.y += acceleration.y * deltaTime;
        // Make sure the object's velocity does not exceed the
        // positive or negative terminal velocity
        velocity.y = MathUtils.clamp(velocity.y, -terminalVelocity.y,
                terminalVelocity.y);
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
