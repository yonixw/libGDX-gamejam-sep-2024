package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;

import io.github.yonixw.AdventureInventory.Utils.ClickableActor;
import io.github.yonixw.AdventureInventory.Utils.ClickableActor.IClickable;

public class ItemBox extends Actor implements IClickable {

    private TextureRegion _Tx;
    private TextureRegion _Elm;

    public ItemBox(TextureRegion Tx, TextureRegion element) {
        _Tx = Tx;
        _Elm = element;

        setBounds(_Tx.getRegionX(), _Tx.getRegionY(),
                _Tx.getRegionWidth(), _Tx.getRegionHeight());
        setOrigin(Align.bottomLeft);

        addListener(new ClickableActor(this));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
//        batch.draw(_Tx,
//                getX(), getY(), ItemGroups.PIXELS * scale, ItemGroups.PIXELS * scale);
        batch.draw(_Tx,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());

        if (_Elm != null) {
            batch.setColor(color.r, color.g, color.b, color.a * parentAlpha * 0.5f);
            batch.draw(_Elm,
                    getX(), getY(),
                    getOriginX(), getOriginY(),
                    getWidth(), getHeight(),
                    getScaleX(), getScaleY(), getRotation());
        }
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        Gdx.app.log("ENTER", getParent().getName() + "->" + getName());
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        Gdx.app.log("EXIT", getParent().getName() + "->" + getName());
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        Gdx.app.log("CLICK", getParent().getName() + "->" + getName());
    }

    @Override
    public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
        Gdx.app.log("DOWN", getParent().getName() + "->" + getName());
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        Gdx.app.log("UP", getParent().getName() + "->" + getName());
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        //Gdx.app.log("DRAG", getParent().getName() + "->" + getName());
    }

}
