package com.gildedrose.model;

public class AgedBrieItem extends AbstractItem {

    public AgedBrieItem(Item item) {
        super(item);
    }

    @Override
    protected void changeQuality() {
        increaseQuality();
        if (getSellIn() < 0) increaseQuality();
    }
}
