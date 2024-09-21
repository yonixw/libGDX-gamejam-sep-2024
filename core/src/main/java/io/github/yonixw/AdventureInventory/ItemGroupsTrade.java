package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ItemGroupsTrade extends ItemGroups {

    Image img;
    OkButton ok;

    // type from 0-4 (Earth,Fire,Water,Air,Nutral) * 2 (Single, Multi)
    public ItemGroupsTrade(TextureRegion[][] sysTx, int w, int h, int type, TextureRegion header, Runnable clicked) {
        super(sysTx, w, h, type);
        TextShift = 32;

        img = new Image(header);
        addActor(img);

        ok = new OkButton(clicked);
        addActor(ok);

    }

    @Override
    protected void drawChildren(Batch batch, float parentAlpha) {

        img.setScale(getScaleX());
        ok.setScale(getScaleX());
        img.setPosition(getX() + TextShift, getY());
        ok.setPosition(getX() + getWidth() - 1.5f * TextShift, getY() - 0.25f * TextShift);

        super.drawChildren(batch, parentAlpha);

    }
}
