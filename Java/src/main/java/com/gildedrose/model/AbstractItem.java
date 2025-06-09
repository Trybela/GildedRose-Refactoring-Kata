package com.gildedrose.model;

import com.gildedrose.exeption.IllegalQualityException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class AbstractItem {

    public static final int MAX_QUALITY = 50;
    public static final int MIN_QUALITY = 0;

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

    protected void validate() {
        if (item.quality < MIN_QUALITY || item.quality > MAX_QUALITY)
            throw new IllegalQualityException(item);
    }

    protected abstract void changeQuality();

    public void update() {
        validate();
        decreaseSellIn();
        changeQuality();
    }

}
