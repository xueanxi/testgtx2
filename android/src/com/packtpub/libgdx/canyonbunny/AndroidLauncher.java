package com.packtpub.libgdx.canyonbunny;

import android.app.AlertDialog;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.packtpub.libgdx.canyonbunny.interfaces.INotify;

import java.io.File;

public class AndroidLauncher extends AndroidApplication implements INotify{
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);



		getDataBaseFileName();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();


		initialize(new CanyonBunnyMain2(this), config);
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
	}

	@Override
	public void display() {
		handler.post(new Runnable() {
			@Override
			public void run() {
				AlertDialog.Builder builder =  new AlertDialog.Builder(AndroidLauncher.this);
				builder.setTitle("android title");
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
	}

	@Override
	public String getDataBaseFileName() {
		File file = getExternalFilesDir("db");
		String dbName = null;
		if(!file.exists()){
			file.mkdir();
		}
		dbName = file.getPath()+File.separator+"data.db";
		return dbName;
	}
}
