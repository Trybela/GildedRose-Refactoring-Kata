package com.gildedrose.model;

public class DefaultItem extends AbstractItem {

    public DefaultItem(Item item) {
        super(item);
    }

    @Override
    protected void changeQuality() {
        decreaseQuality();
        if (getSellIn() < 0) decreaseQuality();
    }
}
