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

        //for (int i = 0; i < 5; i++) {
        //    int w = 1 + (int) Math.floor(Math.random() * 5);
        //    int h = 1 + (int) Math.floor(Math.random() * 5);
        //    int y = (int) Math.floor(Math.random() * 100) + 100;
        //    Group MyItems = new ItemGroups(systemRegions, w, h, 1 + 2 * i);
        //    MyItems.setName("ItemGroup_" + i);
        //    MyItems.setPosition(0 + i * 75, y);
        //    MyItems.setScale(2f, 2f);
        //    stage.addActor(MyItems);
        //}
        double sqr = Math.ceil(Math.sqrt(ALL_ITEMS.ALL_LOOT.length));
        Group MyItems = new ItemGroups(systemRegions, (int) sqr, (int) sqr + 1, 1);
        MyItems.setName("ItemGroup_1");
        MyItems.setScale(2f, 2f);
        MyItems.setPosition(0, HEIGHT / 2 / 2);
        stage.addActor(MyItems);

        int i = 0;
        for (AllItems.Item item : ALL_ITEMS.ALL_LOOT) {

            ItemBox follow = (ItemBox) (MyItems.getChild(i));
            Loot l = new Loot(lootRegions[item.row_col[0]][item.row_col[1]], item);
            l.setName(item.name);
            //Gdx.app.log("FOLLOW1/1", follow.getParent().getName() + "->" + follow.getName());
            l.setScale(2f, 2f);
            l.follow(follow);

            follow.myLoot = l;

            i++;
        }

        for (int j = 0; j < 4; j++) {
            AllItems.Item item = ALL_ITEMS.Attack_TwoHand_Sword_L3;
            ItemBox follow = (ItemBox) (MyItems.getChild(i));
            Loot l = new Loot(lootRegions[item.row_col[0]][item.row_col[1]], item);
            l.setName(item.name);
            //Gdx.app.log("FOLLOW1/1", follow.getParent().getName() + "->" + follow.getName());
            l.setScale(2f, 2f);
            l.follow(follow);
            l.myVariation = AllItems.ItemType.values()[1 + j];
            follow.myLoot = l;

            i++;

        }

        for (int j = 0; j < 4; j++) {
            AllItems.Item item = ALL_ITEMS.Armor_Gold;
            ItemBox follow = (ItemBox) (MyItems.getChild(i));
            Loot l = new Loot(lootRegions[item.row_col[0]][item.row_col[1]], item);
            l.setName(item.name);
            //Gdx.app.log("FOLLOW1/1", follow.getParent().getName() + "->" + follow.getName());
            l.setScale(2f, 2f);
            l.follow(follow);
            l.myVariation = AllItems.ItemType.values()[1 + j];
            follow.myLoot = l;

            i++;

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

    //public BitmapFont getFont(String Path, int Size, Color c) {
    //    BitmapFont font;
    //    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Path));
    //    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    //    parameter.size = Size;
    //    parameter.color = c;
    //    font = generator.generateFont(parameter); // font size 12 pixels
    //    font.getData().markupEnabled = true;
    //    generator.dispose(); // don't forget to dispose to avoid memory leaks!
    //    return font;
    //}
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
