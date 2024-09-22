package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    public static Main Instance;

    public Texture systemTexture;
    public TextureRegion[][] systemRegions; // [Y][X]
    public Texture lootTexture;
    public TextureRegion[][] lootRegions; // [Y][X]
    public Stage stage;

    public static BitmapFont fontGroundhog;
    public static BitmapFont fontDino;

    public static final float WIDTH = 720 * 16 / 9, HEIGHT = 720; //or any other values you need

    public static Cursor Cursor;

    public static AllItems ALL_ITEMS = new AllItems();

    @Override
    public void create() {
        Instance = this;

        systemTexture = new Texture("InvTileSet.png");
        lootTexture = new Texture("WeaponAndLoot.png");

        systemRegions = TextureRegion.split(systemTexture, 16, 16);
        lootRegions = TextureRegion.split(lootTexture, 16, 16);

        fontGroundhog = getNormalFont("fonts/bitmap/groundhog_bmf.fnt", 1, Color.WHITE);
        fontDino = getNormalFont("fonts/bitmap/dinotype_bmf.fnt", 0.5f, Color.BLUE);

        stage = new Stage(new FitViewport(WIDTH, HEIGHT));
        Gdx.input.setInputProcessor(stage);

        CreateElement(5);

        CreateSellers();
        MessageChat mc = new MessageChat(WIDTH, HEIGHT);
        mc.setName("MessageChat");
        stage.addActor(mc);

        Cursor = new Cursor(systemRegions, fontDino);
        Cursor.setName("MAIN_CURSOR");
        Cursor.setScale(4f, 4f);
        stage.addActor(Cursor);

        CC cc = new CC();
        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(this);
        im.addProcessor(stage);
        im.addProcessor(CC.getConsole().getInputProcessor());
        Gdx.input.setInputProcessor(im);

        Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.None);
    }

    private void CreateElement(int count) {
        String[] TypeTitles = new String[]{"Earth", "Fire", "Water", "Air", "No Type"};
        for (int i = 0; i < count; i++) {
            int w = 1 + (int) Math.floor(Math.random() * 5);
            int h = 1 + (int) Math.floor(Math.random() * 5);
            int y = (int) Math.floor(Math.random() * 100) + 100;
            ItemGroups MyItems = new ItemGroups(systemRegions, i == 4 ? 3 : 4, 2, 1 + 2 * i);
            MyItems.setName("ItemGroup_" + i);
            MyItems.setPosition(0, 0);
            MyItems.setScale(2f, 2f);
            MyItems.Title = TypeTitles[i];

            stage.addActor(MyItems);
        }
    }

    private void CreateSellers() {
        // ========= WARRIOR
        ItemGroupsTrade MyItemsWarrior = new ItemGroupsTrade(systemRegions, 4, 2, 9, lootRegions[2][5], () -> {
            Gdx.app.log("CALLBACK", "111111111111111");
        });
        MyItemsWarrior.setName("WarriorGroup");
        MyItemsWarrior.setPosition(0 + 0 * 75, 50);
        MyItemsWarrior.setScale(2f, 2f);
        MyItemsWarrior.Title = "Warrior";
        // ========= WARRIOR
        ItemGroupsTrade MyItemsMagician = new ItemGroupsTrade(systemRegions, 4, 1, 9, lootRegions[2][6], () -> {
            Gdx.app.log("CALLBACK", "2222222222222222");
        });
        MyItemsMagician.setName("MagicianGroup");
        MyItemsMagician.setPosition(0 + 1 * 75, 150);
        MyItemsMagician.setScale(2f, 2f);
        MyItemsMagician.Title = "Wizard";
        // ========= WARRIOR
        ItemGroupsTrade MyItemsMerchant = new ItemGroupsTrade(systemRegions, 5, 1, 9, lootRegions[2][7], () -> {
            Gdx.app.log("CALLBACK", "33333333333333");
        });
        MyItemsMerchant.setName("MerchantGroup");
        MyItemsMerchant.setPosition(0 + 2 * 75, 200);
        MyItemsMerchant.setScale(2f, 2f);
        MyItemsMerchant.Title = "Merchant";

        stage.addActor(MyItemsWarrior);
        stage.addActor(MyItemsMagician);
        stage.addActor(MyItemsMerchant);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //.setProjectionMatrix(stage.getViewport().getCamera().combined);
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();
        CC.draw();

    }

    public BitmapFont getNormalFont(String Path, float Scale, Color c) {
        BitmapFont font = new BitmapFont(Gdx.files.internal(Path));
        font.getData().markupEnabled = true;
        font.getData().setScale(Scale, Scale);
        font.setColor(c);
        return font;
    }

    @Override
    public boolean keyDown(int keycode) {

        if ((keycode == Input.Keys.TAB)) {
            CC.getConsole().setVisible(!CC.getConsole().isVisible());
            CC.getConsole().select();
            CC.getConsole().setSubmitText("");
        }
        return false;
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
