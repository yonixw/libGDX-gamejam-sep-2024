package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.strongjoshua.console.CommandExecutor;
import com.strongjoshua.console.Console;
import com.strongjoshua.console.GUIConsole;

public class CC extends CommandExecutor { // ConsoleCommands

    private static GUIConsole console;

    public CC() {

        Skin skin = new Skin(
                Gdx.files.internal("ingameconsole_default_skin/uiskin.json"),
                new TextureAtlas(Gdx.files.internal("ingameconsole_default_skin/uiskin.atlas"))
        );
        console = new GUIConsole(skin, false);
        console.setCommandExecutor(this);
        console.setSizePercent(50, 50);
        console.enableSubmitButton(true);

        console.printCommands();
    }

    public static Console getConsole() {
        return console;
    }

    public static void draw() {
        if (console.isVisible()) {
            console.draw();
        }
    }

    int printUnder(Group g, int j, String prefix) {
        int _j = j;
        for (Actor elem : g.getChildren()) {
            _j++;
            console.log(prefix + elem.getName() + " (" + _j + ")");
            if (elem instanceof Group) {
                _j = printUnder((Group) elem, _j, prefix + "+");
            }
        }
        return _j;
    }

    int getActor(Actor[] wrapper, int searchJ, Group g, int j, String prefix) {
        int _j = j;
        for (Actor elem : g.getChildren()) {
            _j++;
            if (_j == searchJ) {
                wrapper[0] = elem;
                return _j + 1;
            }
            if (_j > searchJ) {
                return _j;
            }
            if (elem instanceof Group) {
                _j = getActor(wrapper, searchJ, (Group) elem, _j, prefix + "+");
            }
        }
        return _j;
    }

    public void ls() {
        try {
            printUnder(Main.Instance.stage.getRoot(), 0, "");
        } catch (Exception e) {
            console.log(e);
        }
    }

    public void rect(int GroupIndex, float x, float y, float w, float h, float s) {
        try {
            Actor[] result = new Actor[1];
            getActor(result, GroupIndex, Main.Instance.stage.getRoot(), 0, "");
            if (result[0] == null || !(result[0] instanceof Group)) {
                console.log("Index " + GroupIndex + " Not found or not a Group!");
            } else {
                DebugBox db = new DebugBox(x, y, w, h, s);
                db.setName("Debug_for_" + GroupIndex);
                ((Group) result[0]).addActor(db);
            }
        } catch (Exception e) {
            console.log(e);
        }

    }

    public void ls(int GroupIndex) {
        try {
            Actor[] result = new Actor[1];
            getActor(result, GroupIndex, Main.Instance.stage.getRoot(), 0, "");
            if (result[0] == null) {
                console.log("Index " + GroupIndex + " Not found!");
            } else {
                console.log("Pos:" + result[0].getX() + "," + result[0].getY());
                console.log("WH:" + result[0].getWidth() + "," + result[0].getHeight());
                console.log("Sxy:" + result[0].getScaleX() + "," + result[0].getScaleY());
            }
        } catch (Exception e) {
            console.log(e);
        }

    }

    public void mv(int GroupIndex, float x, float y) {
        try {
            Actor[] result = new Actor[1];
            getActor(result, GroupIndex, Main.Instance.stage.getRoot(), 0, "");
            if (result[0] == null) {
                console.log("Index " + GroupIndex + " Not found!");
            } else {
                result[0].setPosition(x, y);
            }
        } catch (Exception e) {
            console.log(e);
        }

    }

    public void loots() {
        for (ItemGroups ig : Main.ElementsSTRG) {
            for (Loot l : ig.getLoots()) {
                console.log(ig.getName() + " -> " + l._myItem.name + "(" + l._follow.getName() + ")");
            }
        }
        for (ItemGroups ig : Main.NPCsSTRG) {
            for (Loot l : ig.getLoots()) {
                console.log(ig.getName() + " -> " + l._myItem.name + "(" + l._follow.getName() + ")");
            }
        }
    }

    public void credit() {
        console.log(Gdx.files.local("CREDIT.txt").readString());
    }

    public void logloot() {
        Gdx.app.log("LOOT", "," + "name,attack,def,health,money");
        for (AllItems.Item item : AllItems.Instance.ALL_LOOT) {
            Gdx.app.log("LOOT", "," + item.name + "," + item.attack + "," + item.defense + "," + item.health + "," + item.money);
        }
    }

    public void q() {
        Gdx.app.log("Hi!", "I am your friendly console");
        console.log("Hi! I am your friendly console");
    }

}
