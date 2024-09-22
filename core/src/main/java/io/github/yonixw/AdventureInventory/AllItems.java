package io.github.yonixw.AdventureInventory;

import java.util.Arrays;
import java.util.stream.Stream;

public class AllItems {

    public enum ItemType {
        Earth,
        Fire,
        Water,
        Air,
        Any
    }

    public class Item {

        public String name;
        public int[] row_col;
        //public String desc; // {E} {MN} {MG} {A} {D}

        public int money; // sell amount for non gold items
        public int attack;
        public int defense;
        public int health;

    }

    public static AllItems Instance;

    // ======================================
    public Item Potion_Big = new Item() {
        {
            name = "Big health potion";
            row_col = new int[]{0, 0};

            health = 15;
            money = 3;
        }
    };
    public Item Potion_Small = new Item() {
        {
            name = "Small health potion";
            row_col = new int[]{1, 0};

            health = 10;
            money = 1;
        }
    };
    public Item Potion_Cup = new Item() {
        {
            name = "Cup of health Potion";
            row_col = new int[]{2, 0};

            health = 5;
            money = 0;
        }
    };

    public Item[] ALL_POTIONS = new Item[]{Potion_Big, Potion_Small, Potion_Cup};

    // ======================================
    public Item Chest_Wood = new Item() {
        {
            name = "Wooden Chest";
            row_col = new int[]{0, 1};

            money = 5;
        }
    };
    public Item Chest_Silver = new Item() {
        {
            name = "Silver Chest";
            row_col = new int[]{1, 1};

            money = 10;
        }
    };
    public Item Chest_Gold = new Item() {
        {
            name = "Golden Chest";
            row_col = new int[]{2, 1};

            money = 15;
        }
    };
    public Item Chest_Diamond = new Item() {
        {
            name = "Diamond Chest";
            row_col = new int[]{3, 1};

            money = 25;
        }
    };
    // ======================================

    public Item Gem_Silver = new Item() {
        {
            name = "Silver Gem";
            row_col = new int[]{3, 3};

            money = 5;
        }
    };
    public Item Gem_Gold = new Item() {
        {
            name = "Gold Gem";
            row_col = new int[]{4, 3};

            money = 10;
        }
    };
    public Item Gem_Diamond = new Item() {
        {
            name = "Diamond Gem";
            row_col = new int[]{2, 3};

            money = 15;
        }
    };
    public Item Coin_Single = new Item() {
        {
            name = "Single Coin";
            row_col = new int[]{4, 1};

            money = 1;
        }
    };
    public Item Coin_Many = new Item() {
        {
            name = "Multiple Coins";
            row_col = new int[]{4, 0};

            money = 5;
        }
    };

    public Item[] ALL_MONEY = new Item[]{
        Chest_Wood, Chest_Silver, Chest_Gold, Chest_Diamond,
        Gem_Silver, Gem_Gold, Gem_Diamond, Coin_Single, Coin_Many};

    // ======================================
    public Item Magic_Blob_Fire = new Item() {
        {
            name = "Magic Blob of Fire";
            row_col = new int[]{0, 2};

            money = 1;
        }
    };
    public Item Magic_Blob_Water = new Item() {
        {
            name = "Magic Blob of Water";
            row_col = new int[]{1, 2};

            money = 1;
        }
    };
    public Item Magic_Blob_Ground = new Item() {
        {
            name = "Magic Blob of Ground";
            row_col = new int[]{2, 2};

            money = 1;
        }
    };
    public Item Magic_Blob_Air = new Item() {
        {
            name = "Magic Blob of Air";
            row_col = new int[]{3, 2};

            money = 1;
        }
    };

    public Item[] ALL_BLOBS = new Item[]{Magic_Blob_Ground, Magic_Blob_Fire, Magic_Blob_Water, Magic_Blob_Air};

    // ======================================
    public Item Armor_Cloth = new Item() {
        {
            name = "Cloth Armor";
            row_col = new int[]{0, 4};

            money = 1;
            defense = 1;
        }
    };
    public Item Armor_Copper = new Item() {
        {
            name = "Copper Armor";
            row_col = new int[]{1, 4};

            money = 1;
            defense = 2;
        }
    };
    public Item Armor_Steel = new Item() {
        {
            name = "Steel Armor";
            row_col = new int[]{2, 4};

            money = 1;
            defense = 5;
        }
    };
    public Item Armor_Gold = new Item() {
        {
            name = "Golden Armor";
            row_col = new int[]{3, 4};

            money = 1;
            defense = 8;
        }
    };
    public Item Armor_Diamond = new Item() {
        {
            name = "Cloth Armor";
            row_col = new int[]{4, 4};

            money = 1;
            defense = 10;
        }
    };

    public Item[] ALL_ARMORS = new Item[]{Armor_Cloth, Armor_Copper, Armor_Steel, Armor_Gold, Armor_Diamond};

    // ======================================
    public Item Attack_TwoHand_Sword_L1 = new Item() {
        {
            name = "Simple Sword";
            row_col = new int[]{0, 7};

            money = 1;
            attack = 3;
        }
    };
    public Item Attack_TwoHand_Sword_L2 = new Item() {
        {
            name = "Advanced Sword";
            row_col = new int[]{0, 6};

            money = 3;
            attack = 5;
        }
    };
    public Item Attack_TwoHand_Sword_L3 = new Item() {
        {
            name = "Supreme Sword";
            row_col = new int[]{0, 5};

            money = 8;
            attack = 8;
        }
    };
    public Item Attack_TwoHand_Axe_L3 = new Item() {
        {
            name = "Supreme Axe";
            row_col = new int[]{1, 5};

            money = 5;
            attack = 10;
        }
    };

    public Item[] ALL_TWO_HAND = new Item[]{
        Attack_TwoHand_Sword_L1, Attack_TwoHand_Sword_L2, Attack_TwoHand_Sword_L3,
        Attack_TwoHand_Axe_L3};

    public Item Attack_OneHand_Axe_L1 = new Item() {
        {
            name = "Simple Axe";
            row_col = new int[]{0, 8};

            money = 1;
            attack = 2;
        }
    };
    public Item Attack_OneHand_Club_L1 = new Item() {
        {
            name = "Simple Club";
            row_col = new int[]{0, 9};

            money = 1;
            attack = 2;
        }
    };
    public Item Attack_OneHand_Mace_L1 = new Item() {
        {
            name = "Simple Mace";
            row_col = new int[]{1, 10};

            money = 1;
            attack = 2;
        }
    };
    public Item Attack_OneHand_Axe_L2 = new Item() {
        {
            name = "Better Axe";
            row_col = new int[]{1, 8};

            money = 3;
            attack = 5;
        }
    };
    public Item Attack_OneHand_Club_L2 = new Item() {
        {
            name = "Better Nails Club";
            row_col = new int[]{1, 9};

            money = 3;
            attack = 5;
        }
    };
    public Item Attack_OneHand_Mace_L2 = new Item() {
        {
            name = "Better Bumpy Mace";
            row_col = new int[]{0, 10};

            money = 3;
            attack = 5;
        }
    };
    public Item Attack_OneHand_Club_L3 = new Item() {
        {
            name = "Legendary Spiky Club";
            row_col = new int[]{1, 6};

            money = 7;
            attack = 6;
        }
    };
    public Item Attack_OneHand_Mace_L3 = new Item() {
        {
            name = "Legendary Saw Mace";
            row_col = new int[]{1, 7};

            money = 7;
            attack = 7;
        }
    };

    public Item[] ALL_ONE_HAND = new Item[]{
        Attack_OneHand_Axe_L1, Attack_OneHand_Club_L1, Attack_OneHand_Mace_L1,
        Attack_OneHand_Axe_L2, Attack_OneHand_Club_L2, Attack_OneHand_Mace_L2,
        Attack_OneHand_Club_L3, Attack_OneHand_Mace_L3
    };

    public Item[] ALL_ATTACK = Stream.concat(Arrays.stream(ALL_TWO_HAND), Arrays.stream(ALL_ONE_HAND)).toArray(Item[]::new);

    // ======================================
    public Item[] GARBAGE_LOOTS = new Item[8];

    void populateGarbage() {
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 4; col++) {
                final int _row = row, _col = col;
                GARBAGE_LOOTS[row * 4 + col] = new Item() {
                    {
                        name = "Garbage";
                        row_col = new int[]{_row + 5, _col};
                        money = 0;
                    }
                };
            }
        }
    }

    public Item[] FOOD_LOOTS = new Item[15];

    void populateFoods() {
        final String[] _names = new String[]{
            "Stake", "Dragon Fruit", "Cheese", "Fish",
            "Eggs", "Honey", "Ananas", "Chicken Tigh",
            "Eggplant", "Spicy Pepper", "Very spicy pepper", "Beetroot",
            "Super ugly worm", "2 Worms", "Fatty Worm"
        };
        final int[] _health = new int[]{
            6, 9, 4, 4,
            4, 5, 3, 3,
            2, 1, 1, 2,
            -2, -2, -1
        };
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (row * 4 + col >= _names.length) {
                    continue;
                }
                final int _row = row, _col = col;

                FOOD_LOOTS[row * 4 + col] = new Item() {
                    {
                        name = _names[_row * 4 + _col];
                        row_col = new int[]{_row + 7, _col};
                        health = _health[_row * 4 + _col];
                        money = (int) Math.ceil(Math.max(0, _health[_row * 4 + _col]) / 2f);
                    }
                };
            }
        }
    }

    public Item[] ALL_LOOT;

    public void populateAllLoot() {
        Item[][] allArrs = new Item[][]{
            ALL_POTIONS, ALL_ARMORS, ALL_ATTACK, ALL_BLOBS, ALL_MONEY, GARBAGE_LOOTS, FOOD_LOOTS
        };
        int count = 0;
        for (Item[] itmArr : allArrs) {
            count += itmArr.length;
        }

        ALL_LOOT = new Item[count];
        int i = 0;
        for (Item[] itmArr : allArrs) {
            for (Item itm : itmArr) {
                ALL_LOOT[i] = itm;
                i++;
            }
        }

    }

    public AllItems() {
        populateFoods();
        populateGarbage();
        populateAllLoot();
        Instance = this;
    }

}
