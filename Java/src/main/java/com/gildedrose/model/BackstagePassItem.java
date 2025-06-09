package com.gildedrose.model;

public class BackstagePassItem extends AbstractItem {

    private static final int THRESHOLD_DOUBLE_INCREASE = 10;
    private static final int THRESHOLD_TRIPLE_INCREASE = 5;

    public BackstagePassItem(Item item) {
        super(item);
    }

    @Override
    public void update() {
        decreaseSellIn();
        increaseQuality();
        if (getSellIn() < THRESHOLD_DOUBLE_INCREASE) increaseQuality();
        if (getSellIn() < THRESHOLD_TRIPLE_INCREASE) increaseQuality();

        if (getSellIn() < 0) {
            resetQuality();
        }
    }
}

