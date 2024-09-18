package io.github.yonixw.AdventureInventory;

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
    private TextureRegion _Mark;

    public ItemBox(TextureRegion Tx, TextureRegion element, TextureRegion marker) {
        _Tx = Tx;
        _Elm = element;
        _Mark = marker;

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

        // highlight me if selected
        if (_Elm != null) {
            batch.setColor(color.r, color.g, color.b, color.a * parentAlpha * 0.5f);
            batch.draw(_Elm,
                    getX(), getY(),
                    getOriginX(), getOriginY(),
                    getWidth(), getHeight(),
                    getScaleX(), getScaleY(), getRotation());
        }

        if (Main.Cursor.lastEnterBox == null || !Main.Cursor.lastEnterBox.equals(this)) {
        } else {
            batch.draw(_Mark,
                    getX(), getY(),
                    getOriginX(), getOriginY(),
                    getWidth(), getHeight(),
                    getScaleX(), getScaleY(), getRotation());
        }

    }

    public Loot myLoot;

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        //Gdx.app.log("ENTER", getParent().getName() + "->" + getName());
        Main.Cursor.lastEnterBox = this;
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        //Gdx.app.log("EXIT", getParent().getName() + "->" + getName());
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        //Gdx.app.log("DOWN", getParent().getName() + "->" + getName());
        if (myLoot != null) {
            Main.Cursor.dragLoot = myLoot;
            Main.Cursor.lastEnterBox = this;
            myLoot.follow(Main.Cursor);
            myLoot = null;
        } else if (myLoot == null && Main.Cursor.dragLoot != null) {
            // Missed click
            myLoot = Main.Cursor.dragLoot;
            Main.Cursor.dragLoot = null;
            Main.Cursor.lastEnterBox = this;
            myLoot.follow(this);
        }
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        //Gdx.app.log("UP", getParent().getName() + "->" + getName());
        if (Main.Cursor.dragLoot != null) {
            // Missed click
            myLoot = null;
            Main.Cursor.lastEnterBox.myLoot = Main.Cursor.dragLoot;
            Main.Cursor.dragLoot = null;
            Main.Cursor.lastEnterBox.myLoot.follow(Main.Cursor.lastEnterBox);
        }
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        //Gdx.app.log("DRAG", getParent().getName() + "->" + getName());
        // Note: bad for me because end of drag still use start of drag
    }

}
