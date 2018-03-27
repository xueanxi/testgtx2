package com.packtpub.libgdx.canyonbunny.utils;

import com.packtpub.libgdx.canyonbunny.CanyonBunnyMain;
import com.badlogic.gdx.tools.texturepacker.*;

/**
 * 这个类用于打包图片，在游戏实际运行中，不许要运行。
 */

public class TexturePackageUtils {
    public static void main (String[] arg) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxWidth = 1024;
        settings.maxHeight = 1024;
        settings.debug = false;
        settings.rotation = false;
        TexturePacker.process(settings, "source", "android/assets/image/", "game");
    }
}
