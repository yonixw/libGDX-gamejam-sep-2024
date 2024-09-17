package io.github.yonixw.AdventureInventory.Utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ClickableActor extends ClickListener {

    public interface IClickable {

        void enter(InputEvent event, float x, float y, int pointer, Actor fromActor);

        void exit(InputEvent event, float x, float y, int pointer, Actor toActor);

        void clicked(InputEvent event, float x, float y);

        void touchDown(InputEvent event, float x, float y, int pointer, int button);

        void touchUp(InputEvent event, float x, float y, int pointer, int button);

        void touchDragged(InputEvent event, float x, float y, int pointer);
    }

    IClickable _parent;

    public ClickableActor(IClickable parent) {
        _parent = parent;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
        _parent.enter(event, x, y, pointer, fromActor);
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        super.exit(event, x, y, pointer, toActor);
        _parent.exit(event, x, y, pointer, toActor);
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        _parent.clicked(event, x, y);
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        boolean result = super.touchDown(event, x, y, pointer, button);
        _parent.touchDown(event, x, y, pointer, button);
        return result;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
        _parent.touchUp(event, x, y, pointer, button);
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        super.touchDragged(event, x, y, pointer);
        _parent.touchDragged(event, x, y, pointer);
    }
}
