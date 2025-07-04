package com.gildedrose.model;

public class SulfurasItem extends AbstractItem {

    public static final int CONSTANT_QUALITY = 80;

    public SulfurasItem(Item item) {
        super(item);
    }

    @Override
    protected void changeQuality() {
        // Legendary item - nothing happens
    }

    @Override
    public void update() {
        setQuality(CONSTANT_QUALITY);
    }
}
