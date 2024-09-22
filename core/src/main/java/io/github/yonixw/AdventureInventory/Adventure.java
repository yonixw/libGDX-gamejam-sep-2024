package io.github.yonixw.AdventureInventory;

import com.badlogic.gdx.graphics.Color;

import io.github.yonixw.AdventureInventory.AllItems.ItemType;

public class Adventure {

    public void Start() {
        MessageChat.Instance.addText(MessageChat.Instance.ft()
                .h1("Adventure Start!").n()
                .ul()
                .li().s("Fight monsters").n()
                .li().s("Collect loot").n()
                .li().s("Pay NPCs").n()
                .li().s("Find truths!").n()
                .li().s("Defeat the dragon").n()
                .s("Fight with Warrior by").n()
                .s("clicking the [GREEN]").s(15, 11).n()
                .s("[]or ignore monster with").n()
                .s("any other npc [GREEN]").s(15, 11).s("[]").n()
        );

        addLoot(AllItems.Instance.Attack_OneHand_Club_L1, Main.NPCsSTRG[Main.NPC.Warrior.ordinal()]);

        newMonster(First_Monster_Lvl1);
    }

    Monster _current = null;

    public String xy(int row, int col) {
        return new String(Character.toChars(row * 16 + col));
    }

    public void newMonster(Monster m) {
        _current = m;
        MessageChat.Instance.addText(MessageChat.Instance.ft()
                .h1("[RED]" + xy(0, 1) + "[] Monster Spawned", 7).n()
                .ul()
                .li().s(m.name).n()
                .li().s(m.attack).s(1, 8, Color.YELLOW).s(" ").s(m.health).s(0, 3, Color.RED).n()
                .li().s("TYPE: " + colorType(m.myT))
        );
    }

    public void WarriorAttack() {

    }

    public class Monster {

        public String name;

        AllItems.ItemType myT;
        public int attack;
        public int health;

        AllItems.Item[] normalLoot;
        AllItems.Item[] rareLoot;
        AllItems.Item[] extremeLoot;

        String secret = "";
    }

    public String colorType(AllItems.ItemType type) {
        String result = "";
        switch (type) {
            case Air:
                result += xy(15, 7) + " Air";
                break;
            case Fire:
                result += fcc(Color.RED, "Fire");
                break;
            case Earth:
                result += fcc(Color.BROWN, "Earth");
                break;
            case Water:
                result += fcc(Color.CYAN, "Water");
                break;
            case Any:
                result += xy(2, 10) + " Generic";
                break;
            default:
                result += "Unkown Type";
        }
        return result;
    }

    public String fcc(Color c, String s) {
        // fast color
        return "[#" + c + "]" + s + "[]";
    }

    public void addLoot(AllItems.Item item, ItemGroups group) {
        ItemBox firstEmpty = group.firstEmpty();
        if (firstEmpty == null) {
            // TODO animation of drop
            return;
        }

        Loot l = new Loot(Main.Instance.lootRegions[item.row_col[0]][item.row_col[1]], item);
        l.setName(item.name);
        l.setScale(2f, 2f);
        l.follow(firstEmpty);

        firstEmpty.myLoot = l;

    }

    public Monster First_Monster_Lvl1 = new Monster() {
        {
            name = fcc(Color.GREEN, "Bloby Slime");
            myT = ItemType.Water;

            attack = 1;
            health = 2;

            secret = "Check for types,\n[BROWN]Earth[] beats [BLUE]Water[].\nAlso, missplaced types will\nbe ruined! Mange carefully!";

            normalLoot = new AllItems.Item[]{
                AllItems.Instance.Attack_OneHand_Club_L1
            };
            rareLoot = new AllItems.Item[0];
            extremeLoot = new AllItems.Item[0];
        }
    };

}
