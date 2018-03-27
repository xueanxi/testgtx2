package com.packtpub.libgdx.canyonbunny.utils;

/**
 * Created by user on 3/22/18.
 */

public class Constants {
    public static final float VIEWPORT_WIDTH = 5;
    public static final float VIEWPORT_HEIGHT = 5;
    public static final String TEXTURE_ATLAS_OBJECTS = "image/game.atlas";
    public static final String FONT_FILE = "font/arial-15.fnt";
    // GUI Width
    public static final float VIEWPORT_GUI_WIDTH = 800.0f;
    // GUI Height
    public static final float VIEWPORT_GUI_HEIGHT = 480.0f;
    // Amount of extra lives at level start
    public static final int LIVES_START = 3;

    public static final int ITEM_FEATHER_POWERUP_DURATION = 3;
    public static final float TIME_DELAY_GAME_OVER = 3;// 挂掉之后等待的时间

    /**
     * 纹理图集的小图名称常量
     */
    public static interface AtlasNames {
        public static final String BUNNY_HEAD = "bunny_head";
        public static final String CLOUD01 = "cloud01";
        public static final String CLOUD02 = "cloud02";
        public static final String CLOUD03 = "cloud03";
        public static final String ITEM_FEATHER = "item_feather";
        public static final String ITEM_GOLD_COIN = "item_gold_coin";
        public static final String MOUNTAIN_LEFT = "mountain_left";
        public static final String MOUNTAIN_RIGHT = "mountain_right";
        public static final String ROCK_EDGE = "rock_edge";
        public static final String ROCK_MIDDLE = "rock_middle";
        public static final String WATER_OVERLAY = "water_overlay";
    }

    public static interface Level{
        public static final String LEVEL_01 = "level/level-01.png";
    }


}
