package com.packtpub.libgdx.canyonbunny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.packtpub.libgdx.canyonbunny.game.AbstractGameScreen;
import com.packtpub.libgdx.canyonbunny.game.GameScreen;
import com.packtpub.libgdx.canyonbunny.game.MenuScreen;
import com.packtpub.libgdx.canyonbunny.utils.Assets;

public class CanyonBunnyMain2 extends Game {
	// http://www.cnblogs.com/mignet/p/Learning_Libgdx_Game_Development_01.html


	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// Load assets
		Assets.getInstance();
		// Start game at menu screen
		setScreen(new MenuScreen(this));
	}
}
