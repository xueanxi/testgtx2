package com.packtpub.libgdx.canyonbunny.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;

/**
 * Created by user on 3/23/18.
 */

public class Assets implements Disposable {
    private static final String TAG = "=Assets";
    public static Assets instance = new Assets();
    private static AssetManager mAssetManager;
    private HashMap<String, AtlasRegion> mTextureMap = new HashMap<String, AtlasRegion>();
    private HashMap<String, Sprite> mSpriteMap = new HashMap<String, Sprite>();
    private static TextureAtlas mTextureAtlas;
    private static BitmapFont bigFont;
    private static BitmapFont midleFont;
    private static BitmapFont smallFont;
    public static Skin defaultSkin;
    public static Skin skinGlassy;
    public static Skin skinShade;
    public static BunnyHead bunnyHead;


    public static SoundAsset soundAsset;

    private static boolean hasInit = false;

    private Assets() {

    }

    public static void init() {
        mAssetManager = new AssetManager();
        mAssetManager.setErrorListener(new MyAssetErrorListener());
        mAssetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
        mAssetManager.load(Constants.TEXTURE_ATLAS_BUNNY, TextureAtlas.class);
        mAssetManager.load(Constants.Sounds.PICKCOIN, Sound.class);
        mAssetManager.load(Constants.Sounds.BGM1, Music.class);
        mAssetManager.finishLoading();
        soundAsset = new SoundAsset(mAssetManager);
        initTextureAtlas();
        initFont();

        defaultSkin = new Skin(Gdx.files.internal(SKIN_LIBGDX_UI),new TextureAtlas(TEXTURE_ATLAS_LIBGDX_UI));
        skinGlassy = new Skin(Gdx.files.internal(SKIN_JSON_GLASSY),new TextureAtlas(SKIN_ATLAS_GLASSY));
        skinShade = new Skin(Gdx.files.internal(SKIN_JSON_SHADE),new TextureAtlas(SKIN_ATLAS_SHADE));
    }

    private static void initTextureAtlas() {
        mTextureAtlas = mAssetManager.get(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
        bunnyHead = new BunnyHead(mAssetManager);
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

    public AtlasRegion findTextureByName(String name) {
        AtlasRegion result = null;
        if (mTextureMap.containsKey(name)) {
            result = mTextureMap.get(name);
        } else {
            result = mTextureAtlas.findRegion(name);
            if (result != null) {
                mTextureMap.put(name, result);
            }
        }
        return result;
    }

    public BitmapFont getBitmapFont(int size) {
        if (size == 3) {
            return bigFont;
        } else if (size == 1) {
            return smallFont;
        } else {
            return midleFont;
        }
    }

    static class MyAssetErrorListener implements AssetErrorListener {

        @Override
        public void error(AssetDescriptor asset, Throwable throwable) {
            Logs.e(TAG, "Error happen in my Assets:" + throwable);
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

    public static class SoundAsset {
        public Sound pickCoin;
        public Music bgm1;

        public SoundAsset(AssetManager am) {
            pickCoin = am.get(Constants.Sounds.PICKCOIN);
            bgm1 = am.get(Constants.Sounds.BGM1);
        }
    }

    public static class BunnyHead{
        Animation<TextureRegion> animation1;
        BunnyHead(AssetManager am){
            TextureAtlas bunnys = am.get(Constants.TEXTURE_ATLAS_BUNNY,TextureAtlas.class);
            /*Array<TextureRegion> regions = new Array<TextureRegion>(5);
            regions.add(bunnys.findRegion(Constants.AtlasBunny.BUNNY_HEAD1));
            regions.add(bunnys.findRegion(Constants.AtlasBunny.BUNNY_HEAD2));
            regions.add(bunnys.findRegion(Constants.AtlasBunny.BUNNY_HEAD3));
            regions.add(bunnys.findRegion(Constants.AtlasBunny.BUNNY_HEAD4));
            regions.add(bunnys.findRegion(Constants.AtlasBunny.BUNNY_HEAD5));*/
            animation1 = new Animation<TextureRegion>(0.05f,bunnys.findRegions("bunnyheads"), Animation.PlayMode.LOOP);
        }

        public Animation getAnimation1() {
            return animation1;
        }
    }


    // Location of description file for skins
    public static final String SKIN_LIBGDX_UI = "skin/default/uiskin.json";
    public static final String TEXTURE_ATLAS_LIBGDX_UI = "skin/default/uiskin.atlas";

    public static final String SKIN_JSON_GLASSY = "skin/glassy/glassy-ui.json";
    public static final String SKIN_ATLAS_GLASSY = "skin/glassy/glassy-ui.atlas";

    public static final String SKIN_JSON_SHADE = "skin/shade/uiskin.json";
    public static final String SKIN_ATLAS_SHADE = "skin/shade/uiskin.atlas";
}
