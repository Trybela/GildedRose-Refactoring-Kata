package com.gildedrose;

import com.gildedrose.lifecycle.ItemLifecycleUpdater;
import com.gildedrose.model.Item;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.gildedrose.constant.ItemsNameConstant.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemLifecycleUnitTest {

    private static final int SELL_IN = 5;
    private static final int QUALITY = 10;
    private static final String DEFAULT_ITEM = "TestDefaultItem";

    @Test
    public void qualityDegradesNormally() {
        var items = List.of(new Item(DEFAULT_ITEM, SELL_IN, QUALITY));
        var app = new ItemLifecycleUpdater();

        app.processDailyChange(items);

        assertEquals(items.get(0).quality, QUALITY - 1);
    }

    @Test
    public void qualityDegradesTwiceFastOnceSellDateHasPassed() {
        var items = List.of(new Item(DEFAULT_ITEM, 0, QUALITY));
        var app = new ItemLifecycleUpdater();

        app.processDailyChange(items);
        assertEquals(items.get(0).quality, QUALITY - 2);
    }

    @Test
    public void qualityIsNeverNegative() {
        var items = List.of(new Item(DEFAULT_ITEM, SELL_IN, 0));
        var app = new ItemLifecycleUpdater();

        app.processDailyChange(items);
        assertEquals(items.get(0).quality, 0);
    }

    @Test
    public void qualityAgedBrieIncreasesOlderItGets() {
        var items = List.of(new Item(AGED_BRIE, SELL_IN, QUALITY));
        var app = new ItemLifecycleUpdater();

        app.processDailyChange(items);
        assertEquals(items.get(0).quality, QUALITY + 1);
    }

    @Test
    public void qualityItemIsNeverMoreThan50() {
        var items = List.of(new Item(AGED_BRIE, SELL_IN, 50));
        var app = new ItemLifecycleUpdater();

        app.processDailyChange(items);
        assertEquals(items.get(0).quality, 50);
    }

    @Test
    public void sulfurasNeverNeedsToBeSoldOrDecreasesInQuality() {
        var items = List.of(new Item(SULFURAS, SELL_IN, QUALITY));
        var app = new ItemLifecycleUpdater();

        app.processDailyChange(items);
        assertEquals(items.get(0).sellIn, SELL_IN);
        assertEquals(items.get(0).quality, QUALITY);
    }

    @Test
    public void qualityBackstagePassesIncreasesBy1WhenMoreThen10Days() {
        var items = List.of(new Item(BACKSTAGE_PASSES, 11, QUALITY));
        var app = new ItemLifecycleUpdater();

        app.processDailyChange(items);
        assertEquals(items.get(0).quality, QUALITY + 1);
    }

    @Test
    public void qualityBackstagePassesIncreasesBy2When10DaysOrLess() {
        var items = List.of(new Item(BACKSTAGE_PASSES, 10, QUALITY));
        var app = new ItemLifecycleUpdater();

        app.processDailyChange(items);
        assertEquals(items.get(0).quality, QUALITY + 2);
    }

    @Test
    public void qualityBackstagePassesIncreasesBy3When5DaysOrLess() {
        var items = List.of(new Item(BACKSTAGE_PASSES, 5, QUALITY));
        var app = new ItemLifecycleUpdater();

        app.processDailyChange(items);
        assertEquals(items.get(0).quality, QUALITY + 3);
    }

    @Test
    public void qualityBackstagePassesDropsTo0With0Days() {
        var items = List.of(new Item(BACKSTAGE_PASSES, 0, QUALITY));
        var app = new ItemLifecycleUpdater();

        app.processDailyChange(items);
        assertEquals(items.get(0).quality, 0);
    }

    @Test
    public void qualityConjuredDegradeTwiceFast() {
        var items = List.of(new Item(CONJURED_MANA_CAKE, SELL_IN, QUALITY));
        var app = new ItemLifecycleUpdater();

        app.processDailyChange(items);
        assertEquals(items.get(0).quality, QUALITY - 2);
    }

    @Test
    public void qualityConjuredDegradeTwiceFastWhenDaysLess0() {
        var items = List.of(new Item(CONJURED_MANA_CAKE, 0, QUALITY));
        var app = new ItemLifecycleUpdater();

        app.processDailyChange(items);
        assertEquals(items.get(0).quality, QUALITY - 4);
    }
}
