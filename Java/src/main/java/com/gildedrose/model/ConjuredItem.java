package com.gildedrose.model;

public class ConjuredItem extends AbstractItem {

    public ConjuredItem(Item item) {
        super(item);
    }

    @Override
    public void update() {
        decreaseSellIn();
        decreaseQuality();
        decreaseQuality();
        if (getSellIn() < 0) {
            decreaseQuality();
            decreaseQuality();
        }
    }
}
