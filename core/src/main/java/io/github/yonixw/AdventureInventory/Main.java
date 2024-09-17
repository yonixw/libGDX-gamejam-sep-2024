package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
    private Stage stage;

    BitmapFont fontGroundhog;
    BitmapFont fontDino;

    public static final float WIDTH = 720 * 16 / 9, HEIGHT = 720; //or any other values you need

    @Override
    public void create() {
        systemTexture = new Texture("TileSet1.png");
        systemRegions = TextureRegion.split(systemTexture, 16, 16);
        fontGroundhog = getFont("fonts/bitmap/groundhog.ttf", 25, Color.WHITE);
        //fontDino = getFont("fonts/bitmap/dinotype2.ttf", 25, Color.WHITE);

        stage = new Stage(new FitViewport(WIDTH, HEIGHT));
        Gdx.input.setInputProcessor(stage);

        Actor txt1 = new MyText(fontGroundhog, "Test [RED]Test []\nAnother Line");
        txt1.setPosition(0, 100);
        Actor txt2 = new MyText(fontGroundhog, "Test Test 2");
        txt2.setPosition(0, 200);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                sb.append(Character.toString(i * 16 + j));
            }
            sb.append('\n');
        }

        Actor txt3 = new MyText(fontGroundhog, sb.toString());
        txt3.setPosition(0, 300);

        Group g1 = new Group();
        //g1.addActor(txt1);
        //g1.addActor(txt2);
        g1.addActor(txt3);
        g1.setPosition(100, 0);

        stage.addActor(g1);

        Actor curser = new Cursor(systemRegions);
        curser.scaleBy(3);
        stage.addActor(curser);

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

    public BitmapFont getFont(String Path, int Size, Color c) {
        BitmapFont font;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Path));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Size;
        parameter.color = c;
        font = generator.generateFont(parameter); // font size 12 pixels
        font.getData().markupEnabled = true;
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        return font;
    }

    public BitmapFont getNormalFont(String Path, int Size, Color c) {
        BitmapFont font = new BitmapFont(Gdx.files.internal(Path));
        font.getData().markupEnabled = true;
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
