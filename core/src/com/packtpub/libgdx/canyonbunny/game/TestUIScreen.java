package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.packtpub.libgdx.canyonbunny.utils.Assets;

import sun.font.TextLabel;

/**
 * Table文章 : https://github.com/libgdx/libgdx/wiki/Table
 */

public class TestUIScreen extends AbstractGameScreen {
    private static final String TAG = "=TestActionScreen";
    Stage stage;
    Image image;
    Viewport viewport;

    int screenWidth = 800;
    int screenHeight = 480;

    public TestUIScreen(DirectedGame game) {
        super(game);
        viewport = new StretchViewport(screenWidth, screenHeight);
        stage = new Stage(viewport);
        Pixmap pixmap = new Pixmap(300, 300, Pixmap.Format.RGB888);
        pixmap.setColor(Color.BLUE);
        pixmap.fill();

        Skin skin = null;
        skin = Assets.defaultSkin;
        //skin = Assets.skinGlassy;

        Button button = new Button(skin);
        button.setPosition(400,300);
        button.setSize(200,100);

        Window window = new Window("window",skin);
        window.setPosition(20,460);
        window.setSize(200,200);
        window.add("windows texts1");


        Label label = new Label("lable",skin);
        label.setPosition(100,200);
        label.setSize(100,100);


        ScrollPane scrollPane = new ScrollPane(label,skin);
        scrollPane.setPosition(220,460);
        scrollPane.setSize(200,300);

        TextButton textButton = new TextButton("Texttttt",skin);
        textButton.setPosition(300,300);
        textButton.setSize(100,70);

        /*ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.imageUp = new NinePatchDrawable(ninePatch);
        ImageButton imageButton =new ImageButton(imageButtonStyle);
        imageButton.setSize(100,50);
        imageButton.setPosition(500,300);*/

        Label nameLabel = new Label("Name:", skin);
        TextField nameText = new TextField("", skin);
        Label addressLabel = new Label("Address:", skin);
        TextField addressText = new TextField("", skin);

        Table table = new Table();
        //table.setPosition(300,100);
        //table.setFillParent(true);
        table.defaults().width(150);//设置所有cell的默认宽
        table.setSize(300,300);
        //table.left().bottom();
        table.add(nameLabel);
        table.add(nameText).width(100);// pad 就是android 的padding
        //table.add(nameText).width(100).spaceBottom(30); // space 对作为的cell生效，但是对边界不生效
        table.row();
        table.add(addressLabel);
        table.add(addressText).width(100);
        table.setDebug(true);

        Container container = new Container();
        container.setActor(null);// Container只能加一个Actor，比较轻量级

        stage.addActor(label);
        stage.addActor(button);
        stage.addActor(window);
        stage.addActor(scrollPane);
        stage.addActor(textButton);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
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
        return stage;
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
    }
}
