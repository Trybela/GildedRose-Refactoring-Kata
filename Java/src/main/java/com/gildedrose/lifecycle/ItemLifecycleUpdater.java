package com.gildedrose.lifecycle;

import com.gildedrose.enums.ItemType;
import com.gildedrose.model.AbstractItem;
import com.gildedrose.model.Item;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
public class ItemLifecycleUpdater {

    public void processDailyChange(List<Item> items) {
        log.debug("Starting processing of daily change for the following items: {}", items);
        items.forEach(this::processItemDailyChange);
        log.debug("Finished processing of daily change.");
    }

    private void processItemDailyChange(Item item) {
        log.debug("Starting processing of daily change for the following item: {}", item);
        AbstractItem wrappedItem = ItemType.applyFromItem(item);
        wrappedItem.update();
        log.debug("Finished processing of daily change. Result item: {}", item);
    }
}
