package io.github.yonixw.AdventureInventory;

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

    }

    @Override
    public void act(float delta) {
        //setBounds(3 * _W / 4, 0, _W / 4, _H);
    }

    public class FastText {

        StringBuilder sb = new StringBuilder();

        public FastText s(int row, int col, int repeat) {
            char[] c = Character.toChars(row * 16 + col);
            for (int i = 0; i < repeat; i++) {
                sb.append(c);
            }
            return this;
        }

        public FastText s(int row, int col) {
            return s(row, col, 1);
        }

        @Override
        public String toString() {
            return sb.toString();
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        FastText f = new FastText();

        f.s(12, 9).s(12, 13, 24).s(11, 11);

        GlyphLayout gl = new GlyphLayout();
        gl.setText(Main.fontDino, f.toString(), getColor(), getWidth(), Align.topLeft, true);
        Main.fontDino.draw(batch, gl, getX(), getY() + gl.height);

        //Gdx.app.log("MSG C", getX() + "," + getY());
    }

}
