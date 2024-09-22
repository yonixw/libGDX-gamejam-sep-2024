package io.github.yonixw.AdventureInventory;

import io.github.yonixw.AdventureInventory.AllItems.ItemType;

public class Adventure {

    public static void Start() {
        MessageChat.Instance.addText(MessageChat.Instance.ft()
                .h1("Adventure Start!").n()
                .ul()
                .li().s(" Collect loot").n()
                .li().s(" Fight monsters").n()
                .li().s(" Pay workers").n()
                .li().s(" Find truths!").n()
                .s("Fight with Warrior by").n()
                .s("clicking the [GREEN]").s(15, 11).n()
                .s("[]or ignore monster with").n()
                .s("any other npc [GREEN]").s(15, 11).s("[]").n()
        );
    }

    public class Monster {

        public String name;

        AllItems.ItemType myT;
        public int attack;
        public int defense;
        public int health;

        AllItems.Item[] normalLoot;
        AllItems.Item[] rareLoot;
        AllItems.Item[] extremeLoot;

        String secret = "";
    }

    public Monster First_Monster_Lvl1 = new Monster() {
        {
            name = "Green Slime";
            myT = ItemType.Water;

            attack = 1;
            defense = 0;
            health = 2;

            secret = "Check for types, [BROWN]Earth[] beats [BLUE]Water[]!";

            normalLoot = new AllItems.Item[]{
                AllItems.Instance.Attack_OneHand_Club_L1
            };
        }
    };
}
