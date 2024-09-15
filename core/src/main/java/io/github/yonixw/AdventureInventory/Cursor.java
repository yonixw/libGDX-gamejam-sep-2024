package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class Cursor extends Actor {

    private TextureRegion curserOpen = null;
    private TextureRegion curserClosed = null;

    public Cursor(TextureRegion[][] regions) {
        curserOpen = regions[6][4];
        curserClosed = regions[6][3];
        setBounds(curserOpen.getRegionX(), curserOpen.getRegionY(),
                curserOpen.getRegionWidth(), curserOpen.getRegionHeight());
        setOrigin(Align.center);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();
        boolean mouseLeftClick = Gdx.input.isButtonPressed(Input.Buttons.LEFT);

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(mouseLeftClick ? curserClosed : curserOpen,
                mouseX, Gdx.graphics.getHeight() - mouseY, getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

}
