package com.gildedrose.model;

public class DefaultItem extends AbstractItem {

    public DefaultItem(Item item) {
        super(item);
    }

    @Override
    public void update() {
        decreaseSellIn();
        decreaseQuality();
        if (getSellIn() < 0) decreaseQuality();
    }
}
