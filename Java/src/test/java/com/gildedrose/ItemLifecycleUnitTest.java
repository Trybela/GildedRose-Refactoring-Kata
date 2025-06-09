package com.gildedrose;

import com.gildedrose.exception.ItemUpdateException;
import com.gildedrose.lifecycle.ItemLifecycleUpdater;
import com.gildedrose.model.AbstractItem;
import com.gildedrose.model.Item;
import com.gildedrose.model.SulfurasItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.gildedrose.constant.ItemsNameConstant.*;
import static com.gildedrose.model.AbstractItem.MAX_QUALITY;
import static com.gildedrose.model.AbstractItem.MIN_QUALITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ItemLifecycleUnitTest {

    private static final int SELL_IN = 5;
    private static final int QUALITY = 10;
    private static final String DEFAULT_ITEM = "TestDefaultItem";

    @Test
    public void qualityDegradesNormally() {
        var items = List.of(new Item(DEFAULT_ITEM, SELL_IN, QUALITY));
        var app = new ItemLifecycleUpdater();

        List<AbstractItem> result = app.processDailyChange(items);

        assertEquals(QUALITY - 1, result.get(0).getQuality());
    }

    @Test
    public void qualityDegradesTwiceFastOnceSellDateHasPassed() {
        var items = List.of(new Item(DEFAULT_ITEM, 0, QUALITY));
        var app = new ItemLifecycleUpdater();

        List<AbstractItem> result = app.processDailyChange(items);
        assertEquals(QUALITY - 2, result.get(0).getQuality());
    }

    @Test
    public void qualityIsNeverNegative() {
        var items = List.of(new Item(DEFAULT_ITEM, SELL_IN, 0));
        var app = new ItemLifecycleUpdater();

        List<AbstractItem> result =app.processDailyChange(items);
        assertEquals(0, result.get(0).getQuality());
    }

    @Test
    public void qualityAgedBrieIncreasesOlderItGets() {
        var items = List.of(new Item(AGED_BRIE_NAME, SELL_IN, QUALITY));
        var app = new ItemLifecycleUpdater();

        List<AbstractItem> result =app.processDailyChange(items);
        assertEquals(QUALITY + 1, result.get(0).getQuality());
    }

    @Test
    public void qualityItemIsNeverMoreThan50() {
        var items = List.of(new Item(AGED_BRIE_NAME, SELL_IN, 50));
        var app = new ItemLifecycleUpdater();

        List<AbstractItem> result =app.processDailyChange(items);
        assertEquals(50, result.get(0).getQuality());
    }

    @Test
    public void sulfurasNeverNeedsToBeSoldOrDecreasesInQuality() {
        var items = List.of(new Item(SULFURAS_NAME, SELL_IN, SulfurasItem.CONSTANT_QUALITY));
        var app = new ItemLifecycleUpdater();

        List<AbstractItem> result =app.processDailyChange(items);
        assertEquals(SELL_IN, result.get(0).getSellIn());
        assertEquals(SulfurasItem.CONSTANT_QUALITY, result.get(0).getQuality());
    }

    @Test
    public void sulfurasQualityAlwaysShouldEquals80() {
        var items = List.of(new Item(SULFURAS_NAME, SELL_IN, QUALITY));
        var app = new ItemLifecycleUpdater();

        List<AbstractItem> result =app.processDailyChange(items);
        assertEquals(SELL_IN, result.get(0).getSellIn());
        assertEquals(SulfurasItem.CONSTANT_QUALITY, result.get(0).getQuality());
    }

    @Test
    public void qualityBackstagePassesIncreasesBy1WhenMoreThen10Days() {
        var items = List.of(new Item(BACKSTAGE_PASSES_NAME, 11, QUALITY));
        var app = new ItemLifecycleUpdater();

        List<AbstractItem> result =app.processDailyChange(items);
        assertEquals(QUALITY + 1, result.get(0).getQuality());
    }

    @Test
    public void qualityBackstagePassesIncreasesBy2When10DaysOrLess() {
        var items = List.of(new Item(BACKSTAGE_PASSES_NAME, 10, QUALITY));
        var app = new ItemLifecycleUpdater();

        List<AbstractItem> result =app.processDailyChange(items);
        assertEquals(QUALITY + 2, result.get(0).getQuality());
    }

    @Test
    public void qualityBackstagePassesIncreasesBy3When5DaysOrLess() {
        var items = List.of(new Item(BACKSTAGE_PASSES_NAME, 5, QUALITY));
        var app = new ItemLifecycleUpdater();

        List<AbstractItem> result =app.processDailyChange(items);
        assertEquals(QUALITY + 3, result.get(0).getQuality());
    }

    @Test
    public void qualityBackstagePassesDropsTo0With0Days() {
        var items = List.of(new Item(BACKSTAGE_PASSES_NAME, 0, QUALITY));
        var app = new ItemLifecycleUpdater();

        List<AbstractItem> result =app.processDailyChange(items);
        assertEquals(0, result.get(0).getQuality());
    }

    @Test
    public void qualityConjuredDegradeTwiceFast() {
        var items = List.of(new Item(CONJURED_MANA_CAKE_NAME, SELL_IN, QUALITY));
        var app = new ItemLifecycleUpdater();

        List<AbstractItem> result =app.processDailyChange(items);
        assertEquals(QUALITY - 2, result.get(0).getQuality());
    }

    @Test
    public void qualityConjuredDegradeTwiceFastWhenDaysLess0() {
        var items = List.of(new Item(CONJURED_MANA_CAKE_NAME, 0, QUALITY));
        var app = new ItemLifecycleUpdater();

        List<AbstractItem> result =app.processDailyChange(items);
        assertEquals(QUALITY - 4, result.get(0).getQuality());
    }

    @Test
    public void shouldThrowExceptionWhenQualityBiggerThenMax() {
        var items = List.of(new Item(DEFAULT_ITEM, 0, MAX_QUALITY + 1));
        var app = new ItemLifecycleUpdater();

        assertThrows(ItemUpdateException.class, () -> app.processDailyChange(items));
    }

    @Test
    public void shouldThrowExceptionWhenQualityLessAsMin() {
        var items = List.of(new Item(DEFAULT_ITEM, 0, MIN_QUALITY - 1));
        var app = new ItemLifecycleUpdater();

        assertThrows(ItemUpdateException.class, () -> app.processDailyChange(items));
    }
}
