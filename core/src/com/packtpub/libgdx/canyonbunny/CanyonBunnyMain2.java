package com.packtpub.libgdx.canyonbunny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.packtpub.libgdx.canyonbunny.game.DirectedGame;
import com.packtpub.libgdx.canyonbunny.game.MenuScreen;
import com.packtpub.libgdx.canyonbunny.game.ScreenTransitionSlice;
import com.packtpub.libgdx.canyonbunny.game.TestActionScreen;
import com.packtpub.libgdx.canyonbunny.game.TestAnimationScreen;
import com.packtpub.libgdx.canyonbunny.game.TestUIScreen;
import com.packtpub.libgdx.canyonbunny.game.TestViewPortScreen;
import com.packtpub.libgdx.canyonbunny.interfaces.ScreenTransition;
import com.packtpub.libgdx.canyonbunny.utils.Assets;
import com.packtpub.libgdx.canyonbunny.utils.AudioManager;

/**
 *  资料：
 *  1.访问网址：http://www.cnblogs.com/mignet/p/Learning_Libgdx_Game_Development_01.html
 *  2.制作粒子效果访问java类文件:ParticleEdit.java
 *  3.打包图片使用utils里面的:TexturePackageUtils
 *  4.声音的制作可以在网站下载：https://www.bfxr.net/
 *  5.制作字体可以使用 Hiero
 */
public class CanyonBunnyMain2 extends DirectedGame {
	//


	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// Load assets
		Assets.init();
		// Start game at menu screen
		//setScreen(new MenuScreen(this),null);
		//setScreen(new TestParticleScreen());

		AudioManager.instance.play(Assets.soundAsset.bgm1,true);

		ScreenTransition transition = ScreenTransitionSlice.init(2,
				ScreenTransitionSlice.UP_DOWN, 10, Interpolation.pow5Out);
		setScreen(new TestUIScreen(this), null);
		//setScreen(new TestAnimationScreen(this), null);
		//setScreen(new TestViewPortScreen(this), null);
	}
}
