package com.gildedrose.lifecycle;

import com.gildedrose.model.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Function;

import static com.gildedrose.constant.ItemsNameConstant.*;

@Getter
@RequiredArgsConstructor
public class ItemLifecycleUpdater {

    private final Item[] items;

    private static final Map<String, Function<Item, AbstractItem>> ITEM_RESOLVER = Map.of(
        AGED_BRIE, AgedBrieItem::new,
        BACKSTAGE_PASSES, BackstagePassItem::new,
        CONJURED_MANA_CAKE, ConjuredItem::new,
        SULFURAS, SulfurasItem::new
    );


    public void processDailyChange() {
        for (Item item : items) {
            processItemDailyChange(item);
        }
    }

    private void processItemDailyChange(Item item) {
        AbstractItem wrappedItem = ITEM_RESOLVER.getOrDefault(item.name, DefaultItem::new).apply(item);
        wrappedItem.update();
    }

    public Item getItem(int index) {
        return items[index];
    }
}
