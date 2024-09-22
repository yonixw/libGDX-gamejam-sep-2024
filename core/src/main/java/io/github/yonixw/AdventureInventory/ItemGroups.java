package io.github.yonixw.AdventureInventory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class ItemGroups extends Group {

    TextureRegion[] myGroupTx;
    TextureRegion myElement;

    private int _w, _h;

    ArrayList<Actor> myChild = new ArrayList<Actor>();

    public final static int PIXELS = 16;
    public final static Color AlphaWhite = Color.WHITE.mul(1, 1, 1, 0.7f);

    public String Title = "";

    // type from 0-4 (Earth,Fire,Water,Air,Nutral) * 2 (Single, Multi)
    public ItemGroups(TextureRegion[][] sysTx, int w, int h, int type) {
        int contLine = (type - type % 4) / 4;
        int contOffX = type % 4;

        myGroupTx = new TextureRegion[]{
            sysTx[0 + contLine * 2][0 + contOffX * 2], // corner
            sysTx[0 + contLine * 2][1 + contOffX * 2], // up-H
            sysTx[1 + contLine * 2][0 + contOffX * 2], // left-V
            sysTx[1 + contLine * 2][1 + contOffX * 2],}; // Main container

        if (type / 2 < 4) {
            myElement = sysTx[10][2 + (type / 2)];
        }

        _w = w;
        _h = h;

        for (int wx = 0; wx < _w; wx++) {
            for (int hy = 0; hy < _h; hy++) {
                Actor ax = new ItemBox(myGroupTx[3], myElement, sysTx[3][8]);
                ax.setName("[" + hy + "," + wx + "]");
                myChild.add(ax);
                addActor(ax);
            }
        }
    }

    MoveToAction latestMovment;
    final float marginMovementPrct = 0.05f;
    boolean move = true;

    @Override
    public void act(float delta) {
        super.act(delta);

        if (move) {
            boolean needNewOne = latestMovment == null || getActions().isEmpty();

            if (needNewOne) {
                if (latestMovment != null) {
                    removeAction(latestMovment);
                }

                float minX = -PIXELS;
                float maxX = minX + (Main.WIDTH / 4 - PIXELS * (_w - 2)) * (1 - MessageChat.wPercent);
                float minY = PIXELS * (_h + 1);
                float maxY = minY + Main.HEIGHT / 4;

                latestMovment = Actions.moveTo(
                        //Math.random() > 0.5f ? minX : maxX,
                        minX + (float) Math.random() * (maxX - minX),
                        minY + (float) Math.random() * (maxY - minY),
                        5f);

                addAction(latestMovment);
            }
        }

    }

    protected int TextShift = 0;

    @Override
    protected void drawChildren(Batch batch, float parentAlpha) {

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        float _x = getX();
        float _y = getY();
        float _scale = Math.max(getScaleX(), getScaleY());
        float _size = PIXELS * _scale;

        // top-left corner
        myGroupTx[0].flip(false, false);
        batch.draw(myGroupTx[0], _x, _y, _size, _size);
        myGroupTx[0].flip(false, false);
        // top-right corner
        myGroupTx[0].flip(true, false);
        batch.draw(myGroupTx[0], _x + _size * (_w + 1), _y, _size, _size);
        myGroupTx[0].flip(true, false);
        // bottom-left corner
        myGroupTx[0].flip(false, true);
        batch.draw(myGroupTx[0], _x, _y - _size * (_h + 1), _size, _size);
        myGroupTx[0].flip(false, true);
        // bottom-right corner
        myGroupTx[0].flip(true, true);
        batch.draw(myGroupTx[0], _x + _size * (_w + 1), _y - _size * (_h + 1), _size, _size);
        myGroupTx[0].flip(true, true);

        //setBounds(_x + _size, _y - _size, _size * (_w + 1), _size * (_h + 1));
        for (int i = 0; i < _w; i++) {
            // Tops
            myGroupTx[1].flip(false, false);
            batch.draw(myGroupTx[1], _x + _size * (i + 1), _y, _size, _size);
            myGroupTx[1].flip(false, false);
        }

        for (int i = 0; i < _w; i++) {
            // Bottoms
            myGroupTx[1].flip(false, true);
            batch.draw(myGroupTx[1], _x + _size * (i + 1), _y - _size * (_h + 1), _size, _size);
            myGroupTx[1].flip(false, true);
        }

        for (int i = 0; i < _h; i++) {
            // Lefts
            myGroupTx[2].flip(false, false);
            batch.draw(myGroupTx[2], _x, _y - _size * (i + 1), _size, _size);
            myGroupTx[2].flip(false, false);
        }

        for (int i = 0; i < _h; i++) {
            // rights
            myGroupTx[2].flip(true, false);
            batch.draw(myGroupTx[2], _x + _size * (_w + 1), _y - _size * (i + 1), _size, _size);
            myGroupTx[2].flip(true, false);
        }

        for (int wx = 0; wx < _w; wx++) {
            for (int hy = 0; hy < _h; hy++) {
                Actor child = myChild.get(wx * _h + hy);
                child.setScale(_scale);
                child.setPosition(_x + _size * (wx + 1), _y - _size * (hy + 1));
            }
        }

        super.drawChildren(batch, parentAlpha);

        GlyphLayout gl = new GlyphLayout(Main.fontDino, Title);
        Main.fontDino.setColor(AlphaWhite);
        Main.fontDino.draw(batch, gl, _x + _size + TextShift, _y + gl.height);
    }
}
