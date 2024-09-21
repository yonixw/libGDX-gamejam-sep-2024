package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ItemGroupsTrade extends ItemGroups {

    TextureRegion _header;
    Image img;
    OkButton ok;

    // type from 0-4 (Earth,Fire,Water,Air,Nutral) * 2 (Single, Multi)
    public ItemGroupsTrade(TextureRegion[][] sysTx, int w, int h, int type, TextureRegion header) {
        super(sysTx, w, h, type);
        _header = header;
        TextShift = 32;
    }

    @Override
    protected void drawChildren(Batch batch, float parentAlpha) {

        if (img == null) {
            img = new Image(_header);
            addActor(img);
            img.setScale(getScaleX());

            ok = new OkButton();
            addActor(ok);
            ok.setScale(getScaleX());
        }
        img.setPosition(getX() + 32, getY());
        ok.setPosition(getX() + getWidth(), getY() + getHeight());

        super.drawChildren(batch, parentAlpha);

    }
}
