package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ItemGroupsTrade2Btn extends ItemGroups {

    Image img;
    OkButton ok, cancel, info;
    int _w;

    // type from 0-4 (Earth,Fire,Water,Air,Nutral) * 2 (Single, Multi)
    public ItemGroupsTrade2Btn(TextureRegion[][] sysTx, int w, int h, int type, TextureRegion header,
            Runnable clickedOk, Runnable clickedCancel, Runnable clickedInfo) {
        super(sysTx, w, h, type);
        _w = w;
        TextShift = 32;

        img = new Image(header);
        addActor(img);

        ok = new OkButton(clickedOk, Main.Instance.systemRegions[6][6]);
        addActor(ok);

        cancel = new OkButton(clickedCancel, Main.Instance.systemRegions[6][5]);
        addActor(cancel);

        info = new OkButton(clickedInfo, Main.Instance.systemRegions[9][5]);
        addActor(info);

    }

    @Override
    protected void drawChildren(Batch batch, float parentAlpha) {

        img.setScale(getScaleX());
        img.setPosition(getX() + TextShift, getY());

        ok.setScale(getScaleX());
        ok.setPosition(getX() + (_w + 0.5f) * getScaleX() * 16, getY() - 0.25f * TextShift);

        cancel.setScale(getScaleX());
        cancel.setPosition(getX() + (_w + 0.5f) * getScaleX() * 16, getY() - getScaleX() * 16 - 0.25f * TextShift);

        info.setScale(getScaleX());
        info.setPosition(getX() + (_w + 0.5f) * getScaleX() * 16, getY() - 2 * getScaleX() * 16 - 0.25f * TextShift);

        super.drawChildren(batch, parentAlpha);

    }
}
