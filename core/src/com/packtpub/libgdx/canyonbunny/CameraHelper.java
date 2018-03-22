package com.packtpub.libgdx.canyonbunny;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by user on 3/22/18.
 */

public class CameraHelper {
    private static final String TAG = "=CameraHelper";
    private final float MAX_ZOOM_IN = 0.25F;
    private final float MAX_ZOOM_OUT = 10F;
    private Vector2 position;
    private float zoom;
    private Sprite target;

    CameraHelper() {
        position = new Vector2();
        zoom = 1f;
    }

    public void update(float deltaTime) {
        if (target == null) return;
        position.x = target.getX() + target.getOriginX();
        position.y = target.getY() + target.getOriginY();
    }

    public void setPosition(float x,float y){
        position.x = x;
        position.y = y;
    }

    public Vector2 getPosition(){
        return position;
    }
    
    public void addZoom(float delta){
        setZoom(zoom +delta);
    }

    public void setZoom(float zoom){
        zoom = MathUtils.clamp(zoom,MAX_ZOOM_IN,MAX_ZOOM_OUT);
    }

    public float getZoom(){
        return zoom;
    }

    public void setTarget(Sprite target){
        this.target = target;
    }

    public boolean hasTarget(){
        return target != null;
    }

    public boolean hasTarget(Sprite target){
        return hasTarget() && this.target.equals(target);
    }

    public void applyTo(OrthographicCamera camera){
        camera.position.x = position.x;
        camera.position.y = position.y;
        camera.zoom = zoom;
        camera.update();
    }
}
