package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.packtpub.libgdx.canyonbunny.utils.Logs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 适配不同分辨率的关键就是，保证自己的画面不变形。
 */

public class TestDataBaseScreen extends AbstractGameScreen {
    private static final String TAG = "=TestDataBaseScreen";
    Stage stage;
    Image image;
    Viewport viewport;

    int screenWidth = 800;
    int screenHeight = 480;


    public TestDataBaseScreen(DirectedGame game) {
        super(game);
        viewport = new StretchViewport(screenWidth, screenHeight);
        //viewport = new FillViewport(screenWidth,screenHeight);
        //viewport = new FitViewport(screenWidth,screenHeight);
        stage = new Stage(viewport);
        Pixmap pixmap = new Pixmap(300, 300, Pixmap.Format.RGB888);
        pixmap.setColor(Color.BLUE);
        pixmap.fill();

        image = new Image(new Texture(pixmap));
        stage.addActor(image);

        create();
    }

    public void create () {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+game.getNotify().getDataBaseFileName());
            Statement stat = c.createStatement();
            stat.execute("CREATE TABLE t1 ( " +
                    "   column1 INT  PRIMARY KEY , " +
                    "   column2 INT, " +
                    "   column3 INT, " +
                    "   columnN INT );");
            System.out.println("Opened database successfully");
        } catch ( ClassNotFoundException e ) {
            Logs.e(TAG,"ClassNotFoundException "+e);
        } catch (SQLException e) {
            Logs.e(TAG,"SQLException "+e);
        }
    }

    @Override
    public void render(float delta) {
        //super.render(delta);
        Gdx.gl.glClearColor(0, 255, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getCamera().viewportHeight = screenHeight;
        stage.getCamera().viewportWidth = (screenHeight/ (float)height) * (float)width;
        stage.getCamera().position.set(stage.getCamera().viewportWidth / 2,stage.getCamera().viewportHeight / 2, 0);
        stage.getCamera().update();

    }

    @Override
    public InputProcessor getInputProcessor() {
        return null;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
