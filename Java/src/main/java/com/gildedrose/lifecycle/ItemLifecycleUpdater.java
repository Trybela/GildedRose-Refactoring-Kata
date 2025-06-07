package com.gildedrose.lifecycle;

import com.gildedrose.model.Item;

import static com.gildedrose.constant.ItemsNameConstant.*;

public class ItemLifecycleUpdater {
    public Item[] items;

    public ItemLifecycleUpdater(Item[] items) {
        this.items = items;
    }

    public void processDailyChange() {
        for (Item item : items) {
            processItemDailyChange(item);
        }
    }

    private void processItemDailyChange(Item item) {
        if (!item.name.equals(SULFURAS)) {
            item.sellIn--;
        }

        if (item.name.equals(AGED_BRIE)) {
            updateAgedBrie(item);
        } else if (item.name.equals(BACKSTAGE_PASSES)) {
            updateBackstagePass(item);
        } else if (!item.name.equals(SULFURAS)) {
            updateDefaultItem(item);
        }
    }

    private void updateAgedBrie(Item item) {
        increaseQuality(item);
        if (item.sellIn < 0) increaseQuality(item);
    }

    private void updateBackstagePass(Item item) {
        increaseQuality(item);
        if (item.sellIn < 10) increaseQuality(item);
        if (item.sellIn < 5) increaseQuality(item);

        if (item.sellIn < 0) {
            item.quality = 0;
        }
    }

    private void updateDefaultItem(Item item) {
        decreaseQuality(item);
        if (item.sellIn < 0) decreaseQuality(item);
    }

    private void increaseQuality(Item item) {
        if (item.quality < 50) item.quality++;
    }

    private void decreaseQuality(Item item) {
        if (item.quality > 0) item.quality--;
    }
}
