package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class ItemBox extends Actor {

    private TextureRegion _Tx;

    public ItemBox(TextureRegion Tx, float x, float y) {
        _Tx = Tx;
        setPosition(x, y);
        setBounds(_Tx.getRegionX(), _Tx.getRegionY(),
                _Tx.getRegionWidth(), _Tx.getRegionHeight());
        setOrigin(Align.bottomLeft);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        float scale = Math.max(getScaleX(), getScaleY());

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
//        batch.draw(_Tx,
//                getX(), getY(), ItemGroups.PIXELS * scale, ItemGroups.PIXELS * scale);
        batch.draw(_Tx,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
    }

}
