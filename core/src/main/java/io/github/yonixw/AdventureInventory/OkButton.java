package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.graphics.g2d.Batch;
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

    public OkButton() {
        TextureRegionDrawable up = new TextureRegionDrawable(Main.Instance.systemRegions[6][1]);
        TextureRegionDrawable down = new TextureRegionDrawable(Main.Instance.systemRegions[6][0]);

        btn = new ImageButton(up, down);
        btn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                okCheck.setY(0);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                okCheck.setY(-1);
                return true;
            }
        });
        addActor(btn);

        okCheck = new Image(Main.Instance.systemRegions[6][6]);
        okCheck.setTouchable(Touchable.disabled);

        addActor(okCheck);

    }

    @Override
    public void drawChildren(Batch batch, float parentAlpha) {
        super.drawChildren(batch, parentAlpha);
    }
}
