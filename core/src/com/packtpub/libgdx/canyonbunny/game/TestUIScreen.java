package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
    ProgressBar progressBar;
    Dialog dialog;


    public TestUIScreen(DirectedGame game) {
        super(game);
        viewport = new StretchViewport(screenWidth, screenHeight);
        stage = new Stage(viewport);
        Pixmap pixmap = new Pixmap(300, 300, Pixmap.Format.RGB888);
        pixmap.setColor(Color.BLUE);
        pixmap.fill();

        Skin skin = null;
        skin = Assets.defaultSkin;
        skin = Assets.skinShade;
        skin.getFont("default-font").getData().markupEnabled = true;//设置开启颜色
        skin.getFont("font-button").getData().markupEnabled = true;//设置开启颜色
        skin.getFont("font-label").getData().markupEnabled = true;//设置开启颜色
        skin.getFont("font-title").getData().markupEnabled = true;//设置开启颜色

        Window window = new Window("window",skin);
        window.setPosition(20,460);
        window.setSize(200,200);
        window.add("windows texts1");

        // ========= Dialog ============
        TextButton textButton1 = new TextButton("OK",skin);
        textButton1.setSize(100,50);
        textButton1.pad(20);
        textButton1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.hide();
            }
        });
        TextButton textButton2 = new TextButton("Cancer",skin);
        textButton2.setSize(100,50);
        textButton2.pad(20);
        textButton2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.hide();
            }
        });
        Label labelDialog = new Label("Do you want to [RED]close the dialog ?[]",skin);


        float dialogWidth = 250;
        float dialogHeight = 200;
        dialog = new Dialog("I am a dialog",skin);
        dialog.setSize(dialogWidth,dialogHeight);
        dialog.setPosition((screenWidth-dialogWidth)/2,(screenHeight - dialogHeight)/2);
        Table dialogButtonTable = dialog.getButtonTable();
        dialogButtonTable.add(textButton1);
        dialogButtonTable.add(textButton2);
        dialog.getContentTable().add(labelDialog);


        /*ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.imageUp = new NinePatchDrawable(ninePatch);
        ImageButton imageButton =new ImageButton(imageButtonStyle);
        imageButton.setSize(100,50);
        imageButton.setPosition(500,300);*/

        //========== Table =============
        float tableWidth = 200;
        float tableHeight = 150;
        Label nameLabel = new Label("Name:", skin);
        TextField nameText = new TextField("", skin);
        Label addressLabel = new Label("Address:", skin);
        TextField addressText = new TextField("", skin);
        Table table = new Table();
        table.setSize(tableWidth,tableHeight);
        table.setPosition(screenWidth-tableWidth,screenHeight - tableHeight);
        table.setOrigin(tableWidth/2,tableHeight/2);
        //table.setFillParent(true);
        //table.defaults().width(150);//设置所有cell的默认宽
        //table.left().bottom();
        table.add(nameLabel);
        table.add(nameText).width(100);// pad 就是android 的padding
        //table.add(nameText).width(100).spaceBottom(30); // space 对作为的cell生效，但是对边界不生效
        table.row();
        table.add(addressLabel);
        table.add(addressText).width(100);
        table.setDebug(false);

        Container container = new Container();
        container.setActor(null);// Container只能加一个Actor，比较轻量级

        //======== ScrollPane ============
        float scrollpaneWidth = 150;
        float scrollpaneheight = 200;
        Label label = new Label("",skin);
        label.setSize(100,100);
        label.setText("new text  \n [RED]11111[] \n [YELLOW]1111122222233333333444444555555556666666[] \n [BLUE]11111[] \n 11111 \n 11111  \n 11111  \n 11111  \n 11111  \n 11111  \n 11111  \n 11111");
        label.setWrap(true);
        ScrollPane scrollPane = new ScrollPane(label,skin);
        scrollPane.setSize(scrollpaneWidth,scrollpaneheight);
        scrollPane.setPosition(screenWidth - scrollpaneWidth,0);

        //========== ProgressBar ===============
        float progressBarWidth = 300;
        float progressBarHeight = 30;
        progressBar = new ProgressBar(0,1000,1,false,skin);
        progressBar.setPosition(0,0);
        progressBar.setSize(progressBarWidth,progressBarHeight);
        progressBar.setAnimateDuration(3);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // do something important here, asynchronously to the rendering thread
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // post a Runnable to the rendering thread that processes the result
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        // process the result, e.g. add it to an Array<Result> field of the ApplicationListener.
                        progressBar.setValue(500);
                    }
                });
            }
        }).start();

        stage.addActor(window);
        stage.addActor(scrollPane);
        stage.addActor(table);
        stage.addActor(progressBar);
        stage.addActor(dialog);
    }

    float progress = 0;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        /*if(progressBar != null){
            progressBar.setValue(++progress);
        }*/

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
