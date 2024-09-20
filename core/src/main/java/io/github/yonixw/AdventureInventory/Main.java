package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Super Mario Brothers-like very basic platformer, using a tile map built using
 * <a href="https://www.mapeditor.org/">Tiled</a> and a tileset and sprites by
 * <a href="http://www.vickiwenderlich.com/">Vicky Wenderlich</a></p>
 *
 * Shows simple platformer collision detection as well as on-the-fly map
 * modifications through destructible blocks!
 *
 * @author mzechner
 */
public class Main extends InputAdapter implements ApplicationListener {

    private Texture systemTexture;
    private TextureRegion[][] systemRegions; // [Y][X]
    private Texture lootTexture;
    private TextureRegion[][] lootRegions; // [Y][X]
    private Stage stage;

    public static BitmapFont fontGroundhog;
    public static BitmapFont fontDino;

    public static final float WIDTH = 720 * 16 / 9, HEIGHT = 720; //or any other values you need

    public static Cursor Cursor;

    public static AllItems ALL_ITEMS = new AllItems();

    @Override
    public void create() {
        systemTexture = new Texture("InvTileSet.png");
        lootTexture = new Texture("WeaponAndLoot.png");

        systemRegions = TextureRegion.split(systemTexture, 16, 16);
        lootRegions = TextureRegion.split(lootTexture, 16, 16);

        fontGroundhog = getNormalFont("fonts/bitmap/groundhog_bmf.fnt", 1, Color.WHITE);
        fontDino = getNormalFont("fonts/bitmap/dinotype_bmf.fnt", 0.5f, Color.BLUE);

        stage = new Stage(new FitViewport(WIDTH, HEIGHT));
        Gdx.input.setInputProcessor(stage);

        for (int i = 0; i < 5; i++) {
            int w = 1 + (int) Math.floor(Math.random() * 5);
            int h = 1 + (int) Math.floor(Math.random() * 5);
            int y = (int) Math.floor(Math.random() * 100) + 100;
            Group MyItems = new ItemGroups(systemRegions, w, h, 1 + 2 * i);
            MyItems.setName("ItemGroup_" + i);
            MyItems.setPosition(0 + i * 75, y);
            MyItems.setScale(2f, 2f);

            stage.addActor(MyItems);
        }

        MessageChat mc = new MessageChat(WIDTH, HEIGHT);
        stage.addActor(mc);

        Cursor = new Cursor(systemRegions, fontGroundhog);
        Cursor.setScale(4f, 4f);
        stage.addActor(Cursor);

        Gdx.graphics.setSystemCursor(SystemCursor.None);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //.setProjectionMatrix(stage.getViewport().getCamera().combined);
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();

    }

    public BitmapFont getNormalFont(String Path, float Scale, Color c) {
        BitmapFont font = new BitmapFont(Gdx.files.internal(Path));
        font.getData().markupEnabled = true;
        font.getData().setScale(Scale, Scale);
        font.setColor(c);
        return font;
    }

    @Override
    public void dispose() {
        stage.dispose();
        systemTexture.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        Gdx.app.log("TAG", width + "," + height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

}
