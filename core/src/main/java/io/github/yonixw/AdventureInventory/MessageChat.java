package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;

public class MessageChat extends Actor {

    float _W, _H;
    final float wPercent = 0.25f;

    public MessageChat(float W, float H) {
        setTouchable(Touchable.disabled);
        _W = W;
        _H = H;

        setColor(Color.WHITE);
        setBounds((1 - wPercent) * _W, 0, wPercent * _W, _H);
        setOrigin(Align.bottomRight);

        setScale(0.5f, 0.5f);
    }

    @Override
    public void act(float delta) {
        //setBounds(3 * _W / 4, 0, _W / 4, _H);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        GlyphLayout gl = new GlyphLayout();
        gl.setText(Main.fontDino, "Test\nTest Test Test", getColor(), getWidth(), Align.topLeft, true);
        Main.fontDino.draw(batch, gl, getX(), getY() + gl.height);

        Gdx.app.log("MSG C", getX() + "," + getY());
    }

}
