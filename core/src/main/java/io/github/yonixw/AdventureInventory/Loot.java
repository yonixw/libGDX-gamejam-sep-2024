package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;

public class Loot extends Actor {

    TextureRegion _Tx;
    Actor _follow;
    AllItems.Item _myItem;
    AllItems.ItemType myVariation = AllItems.ItemType.Any;

    public Loot(TextureRegion lootTexture, AllItems.Item myItem) {
        _Tx = lootTexture;
        _myItem = myItem;
        setBounds(_Tx.getRegionX(), _Tx.getRegionY(),
                _Tx.getRegionWidth(), _Tx.getRegionHeight());
        setOrigin(Align.bottomLeft);

        setTouchable(Touchable.disabled);
    }

    public void follow(Actor a) {
        if (getParent() != null) {
            remove();
        }
        _follow = a;
        _follow.getParent().addActor(this);

        if (_follow != null && _follow.equals(Main.Cursor)) {
            setOrigin(Align.center);
        } else {
            setOrigin(Align.bottomLeft);

        }

    }

    @Override
    public void act(float delta) {
        if (_follow != null) {
            setScale(_follow.getScaleX(), _follow.getScaleY());
            setX(_follow.getX());
            setY(_follow.getY());
            //Gdx.app.log("FOLLOW", getX() + "." + getY());
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        Color color = getColor();
        if (myVariation != AllItems.ItemType.Any) {
            switch (myVariation) {
                case Air:
                    color = Color.GRAY;
                    break;
                case Water:
                    color = Color.CYAN;
                    break;
                case Earth:
                    color = Color.BROWN;
                    break;
                case Fire:
                    color = Color.FIREBRICK;
                    break;
                default:
                    break;
            }
        }
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(_Tx,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
    }

}
