package com.gildedrose;

import com.gildedrose.model.Item;

import java.util.List;

import static com.gildedrose.constant.ItemsNameConstant.*;

public class SetUpHelperTest {

    static List<Item> setUpItems() {
        return List.of(
            new Item(DEXTERITY_VEST_NAME, 10, 20),
            new Item(AGED_BRIE_NAME, 2, 0),
            new Item(ELIXIR_OF_THE_MONGOOSE_NAME, 5, 7),
            new Item(SULFURAS_NAME, 0, 80),
            new Item(SULFURAS_NAME, -1, 80),
            new Item(BACKSTAGE_PASSES_NAME, 15, 20),
            new Item(BACKSTAGE_PASSES_NAME, 10, 49),
            new Item(BACKSTAGE_PASSES_NAME, 5, 49),
            new Item(CONJURED_MANA_CAKE_NAME, 3, 6),
            new Item(CONJURED_MANA_CAKE_NAME, 2, 50)
        );
    }
}
