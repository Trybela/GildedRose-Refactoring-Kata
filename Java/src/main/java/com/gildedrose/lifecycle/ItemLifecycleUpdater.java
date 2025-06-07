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
        if (!item.name.equals(AGED_BRIE) && !item.name.equals(BACKSTAGE_PASSES)) {
            if (item.quality > 0 && !item.name.equals(SULFURAS)) {
                item.quality = item.quality - 1;
            }
        } else {
            if (item.quality < 50) {
                item.quality = item.quality + 1;

                if (item.name.equals(BACKSTAGE_PASSES)) {
                    if (item.sellIn < 11 && item.quality < 50) {
                        item.quality = item.quality + 1;
                    }

                    if (item.quality < 50 && item.sellIn < 6) {
                        item.quality = item.quality + 1;

                    }
                }
            }
        }

        if (!item.name.equals(SULFURAS)) {
            item.sellIn = item.sellIn - 1;
        }

        if (item.sellIn < 0) {
            if (!item.name.equals(AGED_BRIE)) {
                if (!item.name.equals(BACKSTAGE_PASSES)) {
                    if (item.quality > 0) {
                        if (!item.name.equals(SULFURAS)) {
                            item.quality = item.quality - 1;
                        }
                    }
                } else {
                    item.quality = 0;
                }
            } else {
                if (item.quality < 50) {
                    item.quality = item.quality + 1;
                }
            }
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

    private void updateSulfurasItem(Item item) {
        return;
    }

    private void increaseQuality(Item item) {
        if (item.quality < 50) item.quality++;
    }

    private void decreaseQuality(Item item) {
        if (item.quality > 0) item.quality--;
    }
}
