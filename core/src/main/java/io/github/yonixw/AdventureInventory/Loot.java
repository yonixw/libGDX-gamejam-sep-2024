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

    public Loot(TextureRegion lootTexture) {
        _Tx = lootTexture;
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
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(_Tx,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
    }

}
