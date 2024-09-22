package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
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

    public void l(String tag) {
        //Gdx.app.log(tag, getParent().getName() + "->" + getName());
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        l("ENTER-1");
        Main.Cursor.lastEnterBox = this;
        ItemGroups myGroup = (ItemGroups) getParent();
        Group root = myGroup.getParent();
        int rootCount = root.getChildren().size;
        root.swapActor(rootCount - 1, rootCount - 2);
        root.swapActor(myGroup, root.getChild(rootCount - 1));
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        //Gdx.app.log("EXIT", getParent().getName() + "->" + getName());
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        //Gdx.app.log("DOWN", getParent().getName() + "->" + getName());

        l("DOWN-1");
        Loot _lastDragLoot = Main.Cursor.dragLoot;

        Main.Cursor.lastEnterBox = this;
        if (myLoot != null && _lastDragLoot == null) {
            l("DOWN-2");
            Main.Cursor.dragLoot = myLoot;
            myLoot.follow(Main.Cursor);
            myLoot = null;
        } else if (myLoot == null && _lastDragLoot != null) {
            l("DOWN-3");
            // Missed click
            myLoot = _lastDragLoot;
            Main.Cursor.dragLoot = null;
            myLoot.follow(this);
            Adventure.Instance.CheckTypes();
        }
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        //Gdx.app.log("UP", getParent().getName() + "->" + getName());
        l("UP-1");
        ItemBox _lastEnterBox = Main.Cursor.lastEnterBox;

        if (Main.Cursor.dragLoot != null && _lastEnterBox.myLoot == null) {
            l("UP-2");
            // Missed click
            _lastEnterBox.myLoot = Main.Cursor.dragLoot;
            Main.Cursor.dragLoot = null;
            _lastEnterBox.myLoot.follow(_lastEnterBox);
            Adventure.Instance.CheckTypes();
        }
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        //Gdx.app.log("DRAG", getParent().getName() + "->" + getName());
        // Note: bad for me because end of drag still use start of drag
    }

}
