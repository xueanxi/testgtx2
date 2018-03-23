package com.packtpub.libgdx.canyonbunny.utils;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
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
    private HashMap<String,AtlasRegion> mAtlasMap = new HashMap<String,AtlasRegion>();
    private static TextureAtlas mTextureAtlas;
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
        Logs.d(TAG,"asset file size = "+mAssetManager.getAssetNames().size);
        for(String name:mAssetManager.getAssetNames()){
            Logs.d(TAG,"asset name:"+name);
        }
    }

    public AtlasRegion findTextureByName(String name){
        AtlasRegion result = null;
        if(mAtlasMap.containsKey(name)){
            result = mAtlasMap.get(name);
        }else{
            result = mTextureAtlas.findRegion(name);
            if(result != null){
                if(name == Constants.AtlasNames.ROCK_EDGE){
                    //result.rotate = true;
                }else if(name == Constants.AtlasNames.ROCK_MIDDLE){
                    //result.rotate = true;
                }
                mAtlasMap.put(name,result);
            }
        }
        return result;
    }

    static class MyAssetErrorListener implements AssetErrorListener {

        @Override
        public void error(AssetDescriptor asset, Throwable throwable) {
            Logs.e(TAG,"Error happen in my Assets:"+throwable);
        }
    }

    @Override
    public void dispose() {
        this.mAssetManager.dispose();
        hasInit = false;
    }
}
