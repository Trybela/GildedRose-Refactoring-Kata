package com.gildedrose.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class AbstractItem {

    private final Item item;

    public String getName() {
        return item.name;
    }

    public int getSellIn() {
        return item.sellIn;
    }

    protected void decreaseSellIn() {
        item.sellIn--;
    }

    protected void increaseQuality() {
        if (item.quality < 50) {
            item.quality++;
        }
    }

    protected void decreaseQuality() {
        if (item.quality > 0) {
            item.quality--;
        }
    }

    protected void resetQuality() {
        item.quality = 0;
    }

    public abstract void update();
}
