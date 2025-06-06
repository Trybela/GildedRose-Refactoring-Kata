package com.gildedrose;

import com.gildedrose.lifecycle.ItemLifecycleUpdater;
import com.gildedrose.model.Item;
import org.junit.jupiter.api.Test;

import static com.gildedrose.constant.ItemsNameConstant.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemLifecycleUnitTest {

    private static final int SELL_IN = 5;
    private static final int QUALITY = 10;
    private static final String DEFAULT_ITEM = "TestDefaultItem";

    @Test
    public void qualityDegradesNormally() {
        var app = new ItemLifecycleUpdater(new Item[]{new Item(DEFAULT_ITEM, SELL_IN, QUALITY)});

        app.processDailyChange();

        assertEquals(app.items[0].quality, QUALITY - 1);
    }

    @Test
    public void qualityDegradesTwiceFastOnceSellDateHasPassed() {
        var app = new ItemLifecycleUpdater(new Item[]{new Item(DEFAULT_ITEM, 0, QUALITY)});

        app.processDailyChange();
        assertEquals(app.items[0].quality, QUALITY - 2);
    }

    @Test
    public void qualityIsNeverNegative() {
        var app = new ItemLifecycleUpdater(new Item[]{new Item(DEFAULT_ITEM, SELL_IN, 0)});

        app.processDailyChange();
        assertEquals(app.items[0].quality, 0);
    }

    @Test
    public void qualityAgedBrieIncreasesOlderItGets() {
        var app = new ItemLifecycleUpdater(new Item[]{new Item(AGED_BRIE, SELL_IN, QUALITY)});

        app.processDailyChange();
        assertEquals(app.items[0].quality, QUALITY + 1);
    }

    @Test
    public void qualityItemIsNeverMoreThan50() {
        var app = new ItemLifecycleUpdater(new Item[]{new Item(AGED_BRIE, SELL_IN, 50)});

        app.processDailyChange();
        assertEquals(app.items[0].quality, 50);
    }

    @Test
    public void sulfurasNeverNeedsToBeSoldOrDecreasesInQuality() {
        var app = new ItemLifecycleUpdater(new Item[]{new Item(SULFURAS, SELL_IN, QUALITY)});

        app.processDailyChange();
        assertEquals(app.items[0].sellIn, SELL_IN);
        assertEquals(app.items[0].quality, QUALITY);
    }

    @Test
    public void qualityBackstagePassesIncreasesBy1WhenMoreThen10Days() {
        var app = new ItemLifecycleUpdater(new Item[]{new Item(BACKSTAGE_PASSES, 11, QUALITY)});

        app.processDailyChange();
        assertEquals(app.items[0].quality, QUALITY + 1);
    }

    @Test
    public void qualityBackstagePassesIncreasesBy2When10DaysOrLess() {
        var app = new ItemLifecycleUpdater(new Item[]{new Item(BACKSTAGE_PASSES, 10, QUALITY)});

        app.processDailyChange();
        assertEquals(app.items[0].quality, QUALITY + 2);
    }

    @Test
    public void qualityBackstagePassesIncreasesBy3When5DaysOrLess() {
        var app = new ItemLifecycleUpdater(new Item[]{new Item(BACKSTAGE_PASSES, 5, QUALITY)});

        app.processDailyChange();
        assertEquals(app.items[0].quality, QUALITY + 3);
    }

    @Test
    public void qualityBackstagePassesDropsTo0With0Days() {
        var app = new ItemLifecycleUpdater(new Item[]{new Item(BACKSTAGE_PASSES, 0, QUALITY)});

        app.processDailyChange();
        assertEquals(app.items[0].quality, 0);
    }
}
