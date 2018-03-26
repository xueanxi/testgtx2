package com.packtpub.libgdx.canyonbunny.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;

/**
 * Created by user on 3/23/18.
 */

public class Assets implements Disposable {
    private static final String TAG = "=Assets";
    private static Assets mInstance;
    private static AssetManager mAssetManager;
    private HashMap<String,AtlasRegion> mTextureMap = new HashMap<String,AtlasRegion>();
    private HashMap<String,Sprite> mSpriteMap = new HashMap<String,Sprite>();
    private static TextureAtlas mTextureAtlas;
    private static BitmapFont bigFont;
    private static BitmapFont midleFont;
    private static BitmapFont smallFont;
    private static boolean hasInit = false;

    private Assets(){

    }

    public static Assets getInstance(){
        if(mInstance == null){
            mInstance = new Assets();
        }
        if(!hasInit){
            init();
            hasInit = true;
        }
        return mInstance;
    }

    private static void init(){
        mAssetManager = new AssetManager();
        mAssetManager.setErrorListener(new MyAssetErrorListener());
        mAssetManager.load(Constants.TEXTURE_ATLAS_OBJECTS,TextureAtlas.class);
        mAssetManager.finishLoading();

        mTextureAtlas = mAssetManager.get(Constants.TEXTURE_ATLAS_OBJECTS,TextureAtlas.class);


        initFont();
    }

    private static void initFont() {
        bigFont = new BitmapFont();
        bigFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        bigFont.getData().setScale(2f);

        midleFont = new BitmapFont();
        midleFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        midleFont.getData().setScale(1f);

        smallFont = new BitmapFont();
        smallFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        smallFont.getData().setScale(0.5f);
    }

    public AtlasRegion findTextureByName(String name){
        AtlasRegion result = null;
        if(mTextureMap.containsKey(name)){
            result = mTextureMap.get(name);
        }else{
            result = mTextureAtlas.findRegion(name);
            if(result != null){
                mTextureMap.put(name,result);
            }
        }
        return result;
    }

    public BitmapFont getBitmapFont(int size){
        if(size == 3){
            return bigFont;
        }else if(size ==1){
            return smallFont;
        }else{
            return midleFont;
        }
    }

    static class MyAssetErrorListener implements AssetErrorListener {

        @Override
        public void error(AssetDescriptor asset, Throwable throwable) {
            Logs.e(TAG,"Error happen in my Assets:"+throwable);
        }
    }

    @Override
    public void dispose() {
        this.mTextureAtlas.dispose();
        this.mTextureMap.clear();
        this.mAssetManager.dispose();
        //this.bitmapFont.dispose();
        hasInit = false;
    }
}
