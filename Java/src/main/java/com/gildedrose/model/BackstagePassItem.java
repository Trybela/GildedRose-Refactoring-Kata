package com.gildedrose.model;

public class BackstagePassItem extends AbstractItem {

    public BackstagePassItem(Item item) {
        super(item);
    }

    @Override
    public void update() {
        decreaseSellIn();
        increaseQuality();
        if (getSellIn() < 10) increaseQuality();
        if (getSellIn() < 5) increaseQuality();

        if (getSellIn() < 0) {
            resetQuality();
        }
    }
}

