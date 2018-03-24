package com.packtpub.libgdx.canyonbunny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CanyonBunnyMain extends ApplicationAdapter {
	//http://www.cnblogs.com/mignet/p/Learning_Libgdx_Game_Development_01.html
	WorldController worldController;
	WorldRenderer worldRenderer;

	//
	boolean isPause = false;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);
		isPause = false;

	}

	@Override
	public void render () {

		if(!isPause){
			worldController.update(Gdx.graphics.getDeltaTime());
		}


		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		worldRenderer.render();
	}

	@Override
	public void resize(int width, int height) {
		//super.resize(width, height);
		worldRenderer.resize(width,height);
	}

	@Override
	public void dispose () {
		worldRenderer.dispose();
	}

	@Override
	public void pause() {
		super.pause();
		isPause = true;
	}

	@Override
	public void resume() {
		super.resume();
		isPause = false;
	}
}
