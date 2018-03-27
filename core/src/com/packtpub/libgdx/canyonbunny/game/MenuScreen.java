package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.packtpub.libgdx.canyonbunny.utils.Constants;

/**
 * Created by user on 3/27/18.
 */

public class MenuScreen extends AbstractGameScreen {


    private Stage stage;
    private Skin skinCanyonBunny;
    // menu
    private Image imgBackground;
    private Image imgLogo;
    private Image imgInfo;
    private Image imgCoins;
    private Image imgBunny;
    private Button btnMenuPlay;
    private Button btnMenuOptions;
    // options
    private Window winOptions;
    private TextButton btnWinOptSave;
    private TextButton btnWinOptCancel;
    private CheckBox chkSound;
    private Slider sldSound;
    private CheckBox chkMusic;
    private Slider sldMusic;
    private SelectBox selCharSkin;
    private Image imgCharSkin;
    private CheckBox chkShowFpsCounter;
    // debug
    private final float DEBUG_REBUILD_INTERVAL = 5.0f;
    private boolean debugEnabled = false;
    private float debugRebuildStage;
    private SpriteBatch batch;
    Texture texture;

    public MenuScreen(Game game) {
        super(game);
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("image/canyonbunny-bg.png"));
    }

    private void rebuildStage() {
        skinCanyonBunny = new Skin(
                Gdx.files.internal(Constants.SKIN_CANYONBUNNY_UI),
                new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
        // build all layers
        Table layerBackground = buildBackgroundLayer();
        Table layerObjects = buildObjectsLayer();
        Table layerLogos = buildLogosLayer();
        Table layerControls = buildControlsLayer();
        Table layerOptionsWindow = buildOptionsWindowLayer();
        // assemble stage for menu screen
        stage.clear();
        Stack stack = new Stack();

        stack.setSize(Constants.VIEWPORT_GUI_WIDTH,
                Constants.VIEWPORT_GUI_HEIGHT);
        stack.add(layerBackground);
        //stack.add(layerObjects);
        //stack.add(layerLogos);
        //stack.add(layerControls);
        //stage.addActor(layerOptionsWindow);
        stage.addActor(stack);
    }

    private Table buildBackgroundLayer() {
        Table layer = new Table();
        // + Background
        //imgBackground = new Image(skinCanyonBunny, "background");

        imgBackground = new Image(new Texture(Gdx.files.internal("image/canyonbunny-bg.png")));
        layer.add(imgBackground);
        return layer;
    }

    private Table buildObjectsLayer() {
        Table layer = new Table();
        // + Coins
        imgCoins = new Image(skinCanyonBunny, "coins");
        layer.addActor(imgCoins);
        imgCoins.setPosition(135, 80);
        // + Bunny
        imgBunny = new Image(skinCanyonBunny, "bunny");
        layer.addActor(imgBunny);
        imgBunny.setPosition(355, 40);
        return layer;
    }

    private Table buildLogosLayer() {
        Table layer = new Table();
        layer.left().top();
        // + Game Logo
        imgLogo = new Image(skinCanyonBunny, "logo");
        layer.add(imgLogo);
        layer.row().expandY();
        // + Info Logos
        imgInfo = new Image(skinCanyonBunny, "info");
        layer.add(imgInfo).bottom();
        if (debugEnabled)
            layer.debug();
        return layer;
    }

    private Table buildControlsLayer() {
        Table layer = new Table();
        layer.left().top();
        // + Game Logo
        imgLogo = new Image(skinCanyonBunny, "logo");
        layer.add(imgLogo);
        layer.row().expandY();
        // + Info Logos
        imgInfo = new Image(skinCanyonBunny, "info");
        layer.add(imgInfo).bottom();
        if (debugEnabled)
            layer.debug();
        return layer;
    }

    private void onPlayClicked() {
        game.setScreen(new GameScreen(game));
    }

    private void onOptionsClicked() {
    }

    private Table buildOptionsWindowLayer() {
        Table layer = new Table();
        return layer;
    }

    @Override
    public void resize(int width, int height) {
        stage.setViewport(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT));
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.dispose();
        skinCanyonBunny.dispose();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        rebuildStage();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        /*if (debugEnabled) {
            debugRebuildStage -= delta;
            if (debugRebuildStage <= 0) {
                debugRebuildStage = DEBUG_REBUILD_INTERVAL;
                rebuildStage();
            }
        }*/
        stage.act(delta);
        stage.draw();

        //batch.begin();
        //batch.draw(texture,0,0);
        //batch.end();
        //Table.drawDebug(stage);
    }
}
