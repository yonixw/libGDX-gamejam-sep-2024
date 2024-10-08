package io.github.yonixw.AdventureInventory;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import io.github.yonixw.AdventureInventory.AllItems.ItemType;

public class Adventure {

    public static Adventure Instance;

    public Adventure() {
        Instance = this;
    }

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
                .s("clicking [RED]").s(7, 8).s("[]").n()
        );

        addLoot(AllItems.Instance.Attack_OneHand_Club_L1, Main.NPCsSTRG[Main.NPC.Warrior.ordinal()], AllItems.ItemType.Any);

        lvl = 0;
        newMonster(false);
    }

    Monster _current = null;
    int lvl = 0; // 0=Welcom,1,2,3=Dragon

    // ==================================
    //          Utils
    // ==================================
    public static String colorType(AllItems.ItemType type) {
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

    public static String fcc(Color c, String s) {
        // fast color
        return "[#" + c + "]" + s + "[]";
    }

    public boolean addLoot(AllItems.Item item, ItemGroups group, AllItems.ItemType type) {
        ItemBox firstEmpty = group.firstEmpty();
        if (firstEmpty == null) {
            // TODO animation of drop
            return false;
        }

        Loot l = new Loot(type, item);
        l.setName(item.name);
        l.setScale(2f, 2f);
        l.follow(firstEmpty);

        firstEmpty.myLoot = l;
        return true;
    }

    public Loot unattachLoot(Loot l) {
        ItemBox f = (ItemBox) l._follow;
        f.myLoot = null;
        l.follow(null);

        return l;
    }

    public static String xy(int row, int col) {
        return new String(Character.toChars(row * 16 + col));
    }

    public void ClearBag() {
        ArrayList<Loot> bagLoot = Main.BagSTRG.getLoots();
        for (Loot l : bagLoot) {
            unattachLoot(l);
        }
        MessageChat.Instance.addText(MessageChat.Instance.ft()
                .ul().li().s("Cleared " + bagLoot.size() + " items")
                .n());
    }

    public static <T> boolean contains(final T[] array, final T v) {
        for (final T e : array) {
            if (e == v || v != null && v.equals(e)) {
                return true;
            }
        }

        return false;
    }

    public static <T> int indexOf(final T[] array, final T v) {
        int i = 0;
        for (final T e : array) {
            if (e == v || v != null && v.equals(e)) {
                return i;
            }
            i++;
        }

        return -1;
    }

    public void AnimateFallingLoot(Loot l) {

    }

    // ==================================
    //          Warriot - Fight
    // ==================================
    public void newMonster(boolean ignored) {
        if (ignored) {
            MessageChat.Instance.addText(MessageChat.Instance.ft()
                    .ul().li().s("Ignored monster.")
                    .n());
        }
        Monster m = All_Monsters[lvl][(int) (All_Monsters[lvl].length * Math.random())];
        _current = m;
        MonsterInfo();
    }

    public void MonsterInfo() {
        Monster m = _current;
        MessageChat.Instance.addText(MessageChat.Instance.ft()
                .h1("[RED]" + xy(0, 1) + "[] Monster Spawned", 7).n()
                .ul()
                .li().s(m.name).n()
                .li().s(m.attack).s(1, 8, Color.YELLOW).s(" ").s(colorType(m.myT)).n()
        );
    }

    public float ElementMatrix(AllItems.ItemType warrior, AllItems.ItemType monster) {
        /*
                   Earth   Fire   Water     Air
            Earth  1       2       0.5      1
            Fire   0.5     1	   0.5	    2
            Water  2       2       1	    0.5
            Air    1       0.5     2        1

         */
        if (warrior == ItemType.Any) {
            return 1;
        }
        // Earth, fire, water, air
        float[][] matrix = new float[][]{
            {1, 2, 0.5f, 1},
            {0.5f, 1, 0.5f, 2},
            {2, 2, 1, 0.5f},
            {1, 0.5f, 2, 1}
        };
        if (warrior == ItemType.Any || monster == ItemType.Any) {
            return 1;
        }
        return matrix[warrior.ordinal()][monster.ordinal()];
    }

    public AllItems.Item randomItem(AllItems.Item[] items) {
        return items[(int) (Math.random() * items.length)];
    }

    public void WarriorAttack() {
        if (Main.Cursor.dragLoot != null) {
            MessageChat.Instance.addText(MessageChat.Instance.ft().s("You must not carry\nany loot to act!").n());
            return;
        }

        float totalDef = 0;
        float totalAttack = 0;
        float totalHealth = 1;
        ArrayList<Loot> warriorLoot = Main.NPCsSTRG[Main.NPC.Warrior.ordinal()].getLoots();
        for (Loot l : warriorLoot) {
            float typeFactor = ElementMatrix(l.myVariation, _current.myT);
            totalAttack += l._myItem.attack * typeFactor;
            totalDef += l._myItem.defense * typeFactor;
            totalHealth += l._myItem.health;
        }
        totalDef = (float) Math.ceil(totalDef);
        totalAttack = (float) Math.ceil(totalAttack);
        totalHealth = (float) Math.ceil(totalHealth);
        MessageChat.Instance.addText(MessageChat.Instance.ft()
                .h1("Warrior attack").n()
                .ul()
                .li().s(1, 8, Color.YELLOW).s("ATK: ").s(totalAttack).n()
                .li().s(1, 9, Color.YELLOW).s("DEF: ").s(totalDef).n()
                .li().s(0, 3, Color.RED).s("HP: ").s(totalHealth)
                .n());
        if (totalAttack >= _current.attack) {
            //won
            float rnd = (float) Math.random();
            int stack_lost = 0;
            int added = 0;

            if (lvl > 0) {
                added = 3;
                AllItems.Item prize1 = randomItem(Math.random() > 0.5f ? AllItems.Instance.getGarbage(lvl) : AllItems.Instance.getMoney(lvl));
                if (!addLoot(prize1, Main.BagSTRG, AllItems.ItemType.Any)) {
                    stack_lost++;
                }

                AllItems.Item prize2 = randomItem(Math.random() > 0.5f ? AllItems.Instance.getHealth(lvl) : AllItems.Instance.getFood(lvl));
                if (!addLoot(prize2, Main.BagSTRG, AllItems.ItemType.Any)) {
                    stack_lost++;
                }

                AllItems.Item prize3 = randomItem(Math.random() > 0.5f ? AllItems.Instance.getAttack(lvl) : AllItems.Instance.getDef(lvl));
                if (!addLoot(prize3, Main.BagSTRG, AllItems.ItemType.Any)) {
                    stack_lost++;
                }

                if (Math.random() > 0.5f) {
                    added++;
                    if (!addLoot(AllItems.Instance.ALL_BLOBS[_current.myT.ordinal()], Main.ElementsSTRG[_current.myT.ordinal()],
                            _current.myT)) {
                        stack_lost++;
                    }
                }

            } else {
                if (!addLoot(AllItems.Instance.ALL_BLOBS[_current.myT.ordinal()], Main.ElementsSTRG[_current.myT.ordinal()],
                        _current.myT)) {
                    stack_lost++;
                }
                added++;
                if (!addLoot(AllItems.Instance.ALL_BLOBS[AllItems.ItemType.Air.ordinal()], Main.ElementsSTRG[AllItems.ItemType.Air.ordinal()],
                        AllItems.ItemType.Air)) {
                    stack_lost++;
                }
                added++;

                for (AllItems.Item loot : new AllItems.Item[]{
                    AllItems.Instance.Attack_OneHand_Club_L2,
                    randomItem(AllItems.Instance.LVL1_Loot_Money),
                    randomItem(AllItems.Instance.LVL1_Loot_Money),
                    randomItem(AllItems.Instance.LVL1_Loot_Food),
                    randomItem(AllItems.Instance.GARBAGE_LOOTS),}) {
                    added++;
                    if (!addLoot(loot, Main.BagSTRG, AllItems.ItemType.Any)) {
                        stack_lost++;
                    }
                }
            }
            lvl = Math.min(lvl + 1, 3);

            MessageChat.FastText ft = MessageChat.Instance.ft()
                    .h1("Warrior Won").n()
                    .ul()
                    .li().s("Your LVL: ").s(lvl).n()
                    .li().s("Got " + added + " loots").n();
            if (rnd > 0.5f) {
                ft.li().s("+Rare loot").n();
            }
            if (rnd > 0.9f) {
                ft.li().s("+Very rare loot").n();
            }
            if (stack_lost > 0) {
                ft.li().s("-Missed " + stack_lost + " loot").n().s("  Mange better :(").n();
            }

            // todo add money
            MessageChat.Instance.addText(
                    ft.cntr(fcc(Color.RED, "TRUTH EARNED:"), 26, 7).n().s(_current.secret)
            );

            Main.Instance.stage.addAction(Actions.sequence(Actions.delay(0.5f), new Action() {
                @Override
                public boolean act(float delta) {
                    newMonster(false);
                    return true;
                }
            }));

        } else {
            float mDamage = _current.attack - totalAttack;
            if (mDamage > totalHealth) {
                // loose random lot (based on lvl)
                int lost = 0;
                for (int i = 0; i < lvl; i++) {
                    if (!warriorLoot.isEmpty()) {
                        lost++;
                        int rndI = (int) (Math.random() * warriorLoot.size());
                        unattachLoot(warriorLoot.get(rndI));
                        warriorLoot.remove(rndI);
                    }
                }
                MessageChat.Instance.addText(MessageChat.Instance.ft()
                        .h1("Warrior Lost").n()
                        .li().s("Your LVL: ").s(lvl).n()
                        .ul().li().s("Lost " + lost + " item").n()
                        .s("You must go on")
                        .n());
                lvl = Math.max(lvl - 1, 0);
            } else {
                int lost = 0;
                for (int i = 0; i < 1; i++) {
                    if (!warriorLoot.isEmpty()) {
                        lost++;
                        int rndI = (int) (Math.random() * warriorLoot.size());
                        unattachLoot(warriorLoot.get(rndI));
                        warriorLoot.remove(rndI);
                    }
                }

                // tie, loose random 1
                MessageChat.Instance.addText(MessageChat.Instance.ft()
                        .h1("Warrior Tie").n()
                        .ul().li().s("Lost " + lost + " item").n()
                        .s("You must go on")
                        .n());

            }
        }
    }

    // ==================================
    //          Magicion
    // ==================================
    public void DoMagic() {
        // weapon/def and blob

        if (Main.Cursor.dragLoot != null) {
            MessageChat.Instance.addText(MessageChat.Instance.ft().s("You must not carry\nany loot to act!").n());
            return;
        }

        ItemGroups Storage = Main.NPCsSTRG[Main.NPC.Magic.ordinal()];
        int storageCapacity = Storage.whSize();

        Loot money = null;
        Loot weap = null;
        Loot xdef = null;
        Loot blobs = null;

        ArrayList<Loot> loots = Storage.getLoots();

        for (Loot l : loots) {
            if (contains(AllItems.Instance.ALL_MONEY, l._myItem)) {
                money = l;
            } else if (contains(AllItems.Instance.ALL_ATTACK, l._myItem)) {
                weap = l;
            } else if (contains(AllItems.Instance.ALL_ARMORS, l._myItem)) {
                xdef = l;
            } else if (contains(AllItems.Instance.ALL_BLOBS, l._myItem)) {
                blobs = l;
            }
        }

        MessageChat.FastText ft = MessageChat.Instance.ft().h1("Magic Fusion").n().ul();

        boolean ready;
        if (money == null || money._myItem.money < 5 || (weap == null && xdef == null) || blobs == null) {
            ft.
                    li().s("You must provide:").n()
                    .li().s("5$ gold ").n()
                    .li().s("Item to fuse").n()
                    .li().s("  Weapon/Armor").n()
                    .li().s("Magic Blob").n();
            MessageChat.Instance.addText(ft);
            return;
        }

        Loot toEnchant = weap == null ? xdef : weap;
        toEnchant.myVariation = AllItems.ItemType.values()[indexOf(AllItems.Instance.ALL_BLOBS, blobs._myItem)];

        for (Loot l : loots) {
            if (l != toEnchant) {
                unattachLoot(l);
            }
        }

        ft.li().s("Fused Item!").n().li().s("Type: ").s(colorType(toEnchant.myVariation)).n();

        MessageChat.Instance.addText(ft);

    }

    public void CheckTypes() {
        MessageChat.FastText ft = MessageChat.Instance.ft().ul();

        AllItems.ItemType[] types = AllItems.ItemType.values();
        for (int i = 0; i < 4; i++) {
            ItemGroups Storage = Main.ElementsSTRG[i];
            int lost = 0;
            for (Loot l : Storage.getLoots()) {
                if (l.myVariation != types[i]) {
                    lost++;
                    unattachLoot(l);
                }
            }
            if (lost > 0) {
                ft.li().s(colorType(types[i])).s(" Mismatch!").n().li().s("Lost ").s(lost).s(" items").n();
            }
        }

        ItemGroups Storage = Main.BagSTRG;
        int lost = 0;
        for (Loot l : Storage.getLoots()) {
            if (l.myVariation != AllItems.ItemType.Any) {
                lost++;
                unattachLoot(l);
            }
        }
        if (lost > 0) {
            ft.li().s("Garbage Mismatch!").n().li().s("Lost ").s(lost).s(" items").n();
        }

        MessageChat.Instance.addText(ft);
    }

// ==================================
//          Seller
// ==================================
    public void Sell() {
        if (Main.Cursor.dragLoot != null) {
            MessageChat.Instance.addText(MessageChat.Instance.ft().s("You must not carry\nany loot to act!").n());
            return;
        }

        int totalMoney = 0;
        ItemGroups Storage = Main.NPCsSTRG[Main.NPC.Sell.ordinal()];
        int storageCapacity = Storage.whSize();
        ArrayList<Loot> sellLoot = Storage.getLoots();
        for (Loot l : sellLoot) {
            totalMoney += l._myItem.money;
            unattachLoot(l);
        }

        int cut = (int) Math.ceil(totalMoney * 0.05f);
        int change = Math.max(0, totalMoney - cut);

        int __left = change;
        while (__left > 0 && Storage.getLoots().size() < storageCapacity) {
            if (__left >= 25) {
                addLoot(AllItems.Instance.Chest_Diamond, Storage, AllItems.ItemType.Any);
                __left -= 25;
            } else if (__left >= 15) {
                addLoot(AllItems.Instance.Gem_Diamond, Storage, AllItems.ItemType.Any);
                __left -= 15;
            } else if (__left >= 10) {
                addLoot(AllItems.Instance.Gem_Gold, Storage, AllItems.ItemType.Any);
                __left -= 10;
            } else if (__left >= 5) {
                addLoot(AllItems.Instance.Coin_Many, Storage, AllItems.ItemType.Any);
                __left -= 5;
            } else if (__left >= 1) {
                addLoot(AllItems.Instance.Coin_Single, Storage, AllItems.ItemType.Any);
                __left -= 1;
            }
        }

        MessageChat.Instance.addText(MessageChat.Instance.ft()
                .h1("Trade").n()
                .ul()
                .li().s(totalMoney).s("$").n()
                .li().s("-5% Fee: ").s(cut).s("$").n()
                .n());

    }

    // ==================================
    //          Monsters Collection 
    // ==================================
    public class Monster {

        public String name;

        AllItems.ItemType myT;
        public int attack;

        String secret = "";
    }

    public Monster M1_L0 = new Monster() {
        {
            name = fcc(Color.GREEN, "Bloby Slime");
            myT = ItemType.Water;

            attack = 1;

            secret = "Check for types,\nAir beats only " + colorType(ItemType.Water) + ".\nAlso, missplaced types will\nbe ruined! Be aware!";
        }
    };

    public Monster M2_L0 = new Monster() {
        {
            name = fcc(Color.RED, "Fire Lizard");
            myT = ItemType.Fire;

            attack = 1;

            secret = "I hate " + colorType(ItemType.Earth) + " or\n" + colorType(ItemType.Water) + " but love\n" + colorType(ItemType.Fire);
        }
    };

    public Monster M3_L0 = new Monster() {
        {
            name = fcc(Color.BROWN, "Earth Worm");
            myT = ItemType.Earth;

            attack = 1;

            secret = "I run from " + colorType(ItemType.Water) + "\nBut fights with " + colorType(ItemType.Fire);
        }
    };

    public Monster M4_L0 = new Monster() {
        {
            name = fcc(Color.WHITE, "L. Ghost");
            myT = ItemType.Air;

            attack = 1;

            secret = "I love cold\nplaces. Very!";
        }
    };

    public Monster[] ALL_Lvl0 = new Monster[]{
        M1_L0, M2_L0, M3_L0, M4_L0};

    // ==================================
    public Monster M1_L1 = new Monster() {
        {
            name = fcc(Color.GREEN, "Bad Fish");
            myT = ItemType.Water;

            attack = 4;

            secret = "Your health will\nprevent full and\nutter lost!";
        }
    };

    public Monster M2_L1 = new Monster() {
        {
            name = fcc(Color.RED, "Giant Lizard");
            myT = ItemType.Fire;

            attack = 5;

            secret = "Use armor for\ndefense from ATK";
        }
    };

    public Monster M3_L1 = new Monster() {
        {
            name = fcc(Color.BROWN, "Rat");
            myT = ItemType.Earth;

            attack = 4;

            secret = "Fuse for \nmultipliers!";
        }
    };

    public Monster M4_L1 = new Monster() {
        {
            name = fcc(Color.WHITE, "B. Ghost");
            myT = ItemType.Air;

            attack = 5;

            secret = "Only elements can\nbe on elements!";
        }
    };

    public Monster[] ALL_Lvl1 = new Monster[]{
        M1_L1, M2_L1, M3_L1, M4_L1
    };

    // ==================================
    public Monster M1_L2 = new Monster() {
        {
            name = fcc(Color.GREEN, "Saw Fish");
            myT = ItemType.Water;

            attack = 11;

            secret = "Lif is messy\nlike this game.";
        }
    };

    public Monster M2_L2 = new Monster() {
        {
            name = fcc(Color.RED, "Fir Hog");
            myT = ItemType.Fire;

            attack = 10;

            secret = "Randomness is\neverywhere\nso just learn\nto process it.";
        }
    };

    public Monster M3_L2 = new Monster() {
        {
            name = fcc(Color.BROWN, "Spike Tree");
            myT = ItemType.Earth;

            attack = 9;

            secret = "Friends\nare you greatest\nassets.";
        }
    };

    public Monster M4_L2 = new Monster() {
        {
            name = fcc(Color.WHITE, "Legend Bird");
            myT = ItemType.Air;

            attack = 8;

            secret = "Finishing stuff\nis fun so\nmake projects\nbite sized.";
        }
    };

    public Monster[] ALL_Lvl2 = new Monster[]{
        M1_L2, M2_L2, M3_L2, M4_L2
    };

    // ==================================
    public Monster M0_L3 = new Monster() {
        {
            name = fcc(Color.RED, xy(0, 12) + " Scorch Dragon");
            myT = ItemType.Fire;

            attack = 25;

            secret = "The process is the\nmost important\npart. But only\nafter it you\ncan enjoy it.\nThe ultimate\ntruth to\nachieve\n\nThanks for playing.\n[THE END]";
        }
    };

    public Monster[] ALL_Lvl3 = new Monster[]{
        M0_L3
    };

    // ==================================
    public Monster[][] All_Monsters = new Monster[][]{
        ALL_Lvl0, ALL_Lvl1, ALL_Lvl2, ALL_Lvl3
    };

}
