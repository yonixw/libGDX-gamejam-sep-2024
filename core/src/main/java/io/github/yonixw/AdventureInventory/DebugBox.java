package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;

public class DebugBox extends Actor {

    Action lateRemove;

    public DebugBox(float x, float y, float w, float h, float s) {

        setBounds(x, y, w, h);
        setScale(s, s);
        setOrigin(Align.bottomLeft);

        setTouchable(Touchable.disabled);

        setDebug(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (lateRemove == null) {
            lateRemove = Actions.sequence(Actions.delay(5), new Action() {
                @Override
                public boolean act(float delta) {
                    this.actor.remove();
                    return true;
                }
            });
            addAction(lateRemove);
        }

    }
