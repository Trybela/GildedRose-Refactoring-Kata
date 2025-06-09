package com.gildedrose.model;

public class ConjuredItem extends AbstractItem {

    public ConjuredItem(Item item) {
        super(item);
    }

    @Override
    protected void changeQuality() {
        decreaseQuality();
        decreaseQuality();
        if (getSellIn() < 0) {
            decreaseQuality();
            decreaseQuality();
        }
    }
}
