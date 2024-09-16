package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class MyText extends Actor {

    BitmapFont _font;
    String _txt;

    public MyText(BitmapFont font, String text) {
        // setBounds(curserOpen.getRegionX(), curserOpen.getRegionY(),
        //         curserOpen.getRegionWidth(), curserOpen.getRegionHeight());
        setOrigin(Align.center);
        _font = font;
        _txt = text;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        _font.draw(batch, _txt, getX(), getY());

    }

}
