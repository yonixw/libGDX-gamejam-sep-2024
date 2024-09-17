package io.github.yonixw.AdventureInventory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class ItemGroups extends Group {

    TextureRegion[] myGroupTx;
    TextureRegion myElement;

    private int _w, _h;

    ArrayList<Actor> myChild = new ArrayList<Actor>();

    public final static int PIXELS = 16;

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
                Actor ax = new ItemBox(myGroupTx[3], myElement);
                myChild.add(ax);
                addActor(ax);
            }
        }
    }

    @Override
    protected void drawChildren(Batch batch, float parentAlpha) {
        super.drawChildren(batch, parentAlpha);

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
    }
}
