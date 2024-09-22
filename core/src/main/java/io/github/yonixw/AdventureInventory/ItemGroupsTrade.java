package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ItemGroupsTrade extends ItemGroups {

    Image img;
    OkButton ok;
    int _w;

    // type from 0-4 (Earth,Fire,Water,Air,Nutral) * 2 (Single, Multi)
    public ItemGroupsTrade(TextureRegion[][] sysTx, int w, int h, int type, TextureRegion header, Runnable clicked) {
        super(sysTx, w, h, type);
        _w = w;

        if (header != null) {
            img = new Image(header);
            addActor(img);
            TextShift = 32;
        }

        ok = new OkButton(clicked, Main.Instance.systemRegions[6][6]);
        addActor(ok);

    }

    @Override
    protected void drawChildren(Batch batch, float parentAlpha) {

        if (img != null) {
            img.setScale(getScaleX());
            img.setPosition(getX() + TextShift, getY());
        }

        ok.setScale(getScaleX());
        ok.setPosition(getX() + (_w + 0.5f) * getScaleX() * 16, getY() - 0.25f * TextShift);

        super.drawChildren(batch, parentAlpha);

    }
}
