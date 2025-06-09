package com.gildedrose.lifecycle;

import com.gildedrose.model.*;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.gildedrose.constant.ItemsNameConstant.*;

@Getter
public class ItemLifecycleUpdater {

    private static final Map<String, Function<Item, AbstractItem>> ITEM_RESOLVER = Map.of(
        AGED_BRIE, AgedBrieItem::new,
        BACKSTAGE_PASSES, BackstagePassItem::new,
        CONJURED_MANA_CAKE, ConjuredItem::new,
        SULFURAS, SulfurasItem::new
    );


    public void processDailyChange(List<Item> items) {
        items.forEach(this::processItemDailyChange);
    }

    private void processItemDailyChange(Item item) {
        AbstractItem wrappedItem = ITEM_RESOLVER.getOrDefault(item.name, DefaultItem::new).apply(item);
        wrappedItem.update();
    }
}
