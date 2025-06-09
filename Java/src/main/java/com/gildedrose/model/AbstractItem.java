package com.gildedrose.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class AbstractItem {

    private static final int MAX_QUALITY = 50;
    private static final int MIN_QUALITY = 0;

    private final Item item;

    public int getSellIn() {
        return item.sellIn;
    }

    protected void decreaseSellIn() {
        item.sellIn--;
    }

    protected void increaseQuality() {
        if (item.quality < MAX_QUALITY) {
            item.quality++;
        }
    }

    protected void decreaseQuality() {
        if (item.quality > MIN_QUALITY) {
            item.quality--;
        }
    }

    protected void resetQuality() {
        item.quality = MIN_QUALITY;
    }

    protected void setQuality(int quality) {
        item.quality = quality;
    }

    public abstract void update();

}
