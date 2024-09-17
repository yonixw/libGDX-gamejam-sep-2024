package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;

public class Cursor extends Actor {

    private TextureRegion curserOpen = null;
    private TextureRegion curserClosed = null;
    private BitmapFont _font;

    public Loot dragLoot;
    public ItemBox lastEnterBox;

    public Cursor(TextureRegion[][] regions, BitmapFont font) {
        curserOpen = regions[4][5];
        curserClosed = regions[4][4];
        _font = font;
        setBounds(curserOpen.getRegionX(), curserOpen.getRegionY(),
                curserOpen.getRegionWidth(), curserOpen.getRegionHeight());
        setOrigin(Align.topRight);
        setTouchable(Touchable.disabled);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        Vector3 mousePos = getStage().getViewport().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        boolean mouseLeftClick = Gdx.input.isButtonPressed(Input.Buttons.LEFT);

        // Gdx.graphics.getHeight()
        setX(mousePos.x);
        setY(mousePos.y);

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(mouseLeftClick ? curserClosed : curserOpen,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());

        _font.draw(batch, Math.round(mousePos.x) + "," + Math.round(mousePos.y), mousePos.x, mousePos.y - 20);
    }
}
