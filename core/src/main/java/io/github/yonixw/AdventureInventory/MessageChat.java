package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;

public class MessageChat extends Actor {

    float _W, _H;
    public static final float wPercent = 0.25f;

    public static MessageChat Instance;

    public MessageChat(float W, float H) {
        setTouchable(Touchable.disabled);
        _W = W;
        _H = H;

        setColor(Color.WHITE);
        setBounds((1 - wPercent) * _W, 0, wPercent * _W, _H);
        setOrigin(Align.bottomRight);

        Instance = this;
    }

    @Override
    public void act(float delta) {
        //setBounds(3 * _W / 4, 0, _W / 4, _H);
    }

    public FastText ft() {
        return new FastText();
    }

    public class FastText {

        StringBuilder sb = new StringBuilder();

        public FastText() {
        }

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

        public FastText s(int row, int col, Color c) {
            return s("[#" + c + "]").s(row, col, 1).s("[]");
        }

        public FastText s(String str) {
            sb.append(str);
            return this;
        }

        public FastText s(int i) {
            sb.append(i);
            return this;
        }

        public FastText n() {
            sb.append("\n");
            return this;
        }

        public FastText cntr(String str, int count, int ignoreCount) {
            if (str.length() - ignoreCount >= count - 1) {
                return s(str.substring(0, count));
            } else {
                int left = (count - str.length() + ignoreCount);
                int diff1 = left / 2;
                return s(0, 0, diff1).s(str).s(0, 0, left - diff1);
            }
        }

        public FastText h1(String str) {
            return h1(str, 0);
        }

        public FastText h1(String str, int ignoreCount) {
            return s(12, 9).s(12, 13, 24).s(11, 11).n()
                    .s(11, 10).cntr(str, 24, ignoreCount).s(11, 10).n()
                    .s(13, 3).s(12, 13, 24).s(11, 13);
        }

        int lists[][] = new int[][]{
            {0, 4}, {0, 3}, {0, 5}, {0, 6}, {0, 7}, {0, 9}, {0, 15},
            {1, 0}, {1, 10},
            {2, 10},
            {3, 14},
            {10, 10}, {10, 15},};

        int[] listIndex = new int[]{0, 4};

        public FastText ul() {
            listIndex = lists[(int) (Math.random() * lists.length)];
            return this;
        }

        public FastText li() {
            return s(listIndex[0], listIndex[1]).s(" ");
        }

        @Override
        public String toString() {
            return sb.toString();
        }

    }

    ShapeRenderer shapeRenderer;
    Color fill = Color.BLACK.mul(1, 1, 1, 0.7f);

    String myData = "";

    public void addText(FastText ft) {
        myData += ft.toString() + "\n";
        if (myData.length() > 1500) {
            myData = myData.substring(myData.length() - 1500);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.end();
        if (shapeRenderer == null) {
            shapeRenderer = new ShapeRenderer();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        }
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(fill);
        shapeRenderer.rect(getX(), 0, _W, _H);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        batch.begin();

        GlyphLayout gl = new GlyphLayout();
        gl.setText(Main.fontDino, myData);

        Main.fontDino.draw(batch, gl, getX(), getY() + gl.height);

        //Gdx.app.log("MSG C", getX() + "," + getY());
    }

}
