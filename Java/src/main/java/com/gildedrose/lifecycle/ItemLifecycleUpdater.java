package com.gildedrose.lifecycle;

import com.gildedrose.enums.ItemType;
import com.gildedrose.model.AbstractItem;
import com.gildedrose.model.Item;
import lombok.Getter;

import java.util.List;

@Getter
public class ItemLifecycleUpdater {

    public void processDailyChange(List<Item> items) {
        items.forEach(this::processItemDailyChange);
    }

    private void processItemDailyChange(Item item) {
        AbstractItem wrappedItem = ItemType.applyFromItem(item);
        wrappedItem.update();
    }
}
