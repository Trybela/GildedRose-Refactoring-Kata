package com.gildedrose;

import com.gildedrose.model.Item;

import java.util.List;

import static com.gildedrose.constant.ItemsNameConstant.*;

public class SetUpHelperTest {

    static List<Item> setUpItems() {
        return List.of(
            new Item(DEXTERITY_VEST, 10, 20),
            new Item(AGED_BRIE, 2, 0),
            new Item(ELIXIR_OF_THE_MONGOOSE, 5, 7),
            new Item(SULFURAS, 0, 80),
            new Item(SULFURAS, -1, 80),
            new Item(BACKSTAGE_PASSES, 15, 20),
            new Item(BACKSTAGE_PASSES, 10, 49),
            new Item(BACKSTAGE_PASSES, 5, 49),
            new Item(CONJURED_MANA_CAKE, 3, 6),
            new Item(CONJURED_MANA_CAKE, 2, 58)
        );
    }
}
