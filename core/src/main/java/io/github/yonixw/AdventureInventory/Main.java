package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture image;

    private int x = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
    }

    @Override
    public void render() {

        int w = Gdx.graphics.getWidth();
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        x = (x + 1) % w;

        batch.draw(image, 0 + x, 210);
        batch.end();

    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
