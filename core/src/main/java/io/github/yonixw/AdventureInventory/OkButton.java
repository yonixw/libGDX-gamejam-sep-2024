package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class OkButton extends Group {

    Image okCheck;
    ImageButton btn;
    Runnable _cb;

    public OkButton(Runnable callback, TextureRegion buttonType) {
        TextureRegionDrawable up = new TextureRegionDrawable(Main.Instance.systemRegions[6][1]);
        TextureRegionDrawable down = new TextureRegionDrawable(Main.Instance.systemRegions[6][0]);
        _cb = callback;

        btn = new ImageButton(up, down);
        btn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                okCheck.setY(0);
                if (_cb != null) {
                    _cb.run();
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                okCheck.setY(-1);
                return true;
            }
        });
        addActor(btn);

        okCheck = new Image(buttonType);
        okCheck.setTouchable(Touchable.disabled);

        addActor(okCheck);

    }

    @Override
    public void drawChildren(Batch batch, float parentAlpha) {
        super.drawChildren(batch, parentAlpha);
    }
}
