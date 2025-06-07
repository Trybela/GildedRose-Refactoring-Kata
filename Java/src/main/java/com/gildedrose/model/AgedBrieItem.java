package com.gildedrose.model;

public class AgedBrieItem extends AbstractItem {

    public AgedBrieItem(Item item) {
        super(item);
    }

    @Override
    public void update() {
        decreaseSellIn();
        increaseQuality();
        if (getSellIn() < 0) increaseQuality();
    }
}
