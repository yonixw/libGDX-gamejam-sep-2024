package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.scenes.scene2d.Group;

public class ItemGroups extends Group {

    public TextureRegion[] myGroupTx;
    private int _w, _h;

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

        _w = w;
        _h = h;
    }

    @Override
    protected void drawChildren(Batch batch, float parentAlpha) {
        super.drawChildren(batch, parentAlpha);

        float _x = getX();
        float _y = getY();
        float _scaleX = getScaleX();
        float _scaleY = getScaleY();

        // top-left corner
        batch.draw(myGroupTx[0], PIXELS, PIXELS,
                new Affine2().translate(_x, _y).scale(_scaleX, _scaleY));
        // top-right corner
        batch.draw(myGroupTx[0], PIXELS, PIXELS,
                new Affine2().translate(_x + PIXELS * _w * _scaleX, _y).scale(-1 * _scaleX, _scaleY));
        for (int i = 0; i < _w; i++) {
            // Tops
            //batch.draw(myGroupTx[1], PIXELS, PIXELS,
            //        new Affine2().translate(_x + PIXELS * i * _scaleX, _y).scale(_scaleX, _scaleY));
        }
        // bottom-left corner
        batch.draw(myGroupTx[0], PIXELS, PIXELS,
                new Affine2().translate(_x, _y - PIXELS * _w * _scaleY).scale(_scaleX, -1 * _scaleY));
        // bottom-right corner
        batch.draw(myGroupTx[0], PIXELS, PIXELS,
                new Affine2().translate(_x + PIXELS * _w * _scaleX, _y - PIXELS * _w * _scaleY).scale(-1 * _scaleX, -1 * _scaleY));

    }
}
