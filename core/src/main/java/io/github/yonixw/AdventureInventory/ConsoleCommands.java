package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.strongjoshua.console.CommandExecutor;
import com.strongjoshua.console.Console;
import com.strongjoshua.console.GUIConsole;

public class ConsoleCommands extends CommandExecutor {

    private static GUIConsole console;

    public ConsoleCommands() {

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

    public void ql1() {
        try {
            printUnder(Main.Instance.stage.getRoot(), 0, "");
        } catch (Exception e) {
            console.log(e);
        }
    }

    public void qp1(int i) {
        try {
            Actor[] result = new Actor[1];
            getActor(result, i, Main.Instance.stage.getRoot(), 0, "");
            if (result[0] == null) {
                console.log("Index " + i + " Not found!");
            } else {
                console.log("Pos:" + result[0].getX() + "," + result[0].getY());
            }
        } catch (Exception e) {
            console.log(e);
        }
    }

    public void q() {
        Gdx.app.log("Hi!", "I am your friendly console");
        console.log("Hi! I am your friendly console");
    }

}
