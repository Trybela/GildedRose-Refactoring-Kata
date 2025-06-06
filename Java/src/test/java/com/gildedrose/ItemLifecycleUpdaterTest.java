package com.gildedrose;

import com.gildedrose.model.Item;
import com.gildedrose.lifecycle.ItemLifecycleUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.IntStream;

import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemLifecycleUpdaterTest {

    private Item[] items;

    @BeforeEach
    void setUpItems() {
        this.items = new Item[]{
            new Item("+5 Dexterity Vest", 10, 20),
            new Item("Aged Brie", 2, 0),
            new Item("Elixir of the Mongoose", 5, 7),
            new Item("Sulfuras, Hand of Ragnaros", 0, 80),
            new Item("Sulfuras, Hand of Ragnaros", -1, 80),
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            new Item("Conjured Mana Cake", 3, 6)
        };
    }

    /**
     * Per‑day verification: Simulates inventory changes day-by-day and verifies every item after each day.
     */
    @ParameterizedTest(name = "Validate inventory after {0} days")
    @MethodSource("expectedItemValuesToDayProvider")
    void inventoryMatchesExpectationsAfterDays(int days, List<TestItem> expectations) {
        ItemLifecycleUpdater app = new ItemLifecycleUpdater(items);

        // Update quality & Assert – day‑by‑day
        for (int day = 0; day < days; day++) {

            app.processDailyChange();

            final int idx = day;
            assertAll("Day " + (idx + 1),
                IntStream.range(0, expectations.size())
                    .mapToObj(i -> (Executable) () ->
                        assertItem(idx, app.items[i], expectations.get(i)))
                    .toArray(Executable[]::new)
            );
        }
    }

    private static void assertItem(int day, Item actualItem, TestItem expectedItem) {
        assertEquals(expectedItem.name(), actualItem.name);
        assertEquals(expectedItem.sellInArray()[day], actualItem.sellIn);
        assertEquals(expectedItem.qualityArray()[day], actualItem.quality);
    }

    /**
     * Set up all expected values for all items.
     * @return number of days and a list of items with their expected sell-in and quality values for
     * each day consequentially.
     */
    private static Stream<Arguments> expectedItemValuesToDayProvider() {
        return Stream.of(
            Arguments.of(30, List.of(
                new TestItem("+5 Dexterity Vest",
                    new int[]{
                        9, 8, 7, 6, 5, 4, 3, 2, 1, 0,
                        -1, -2, -3, -4, -5, -6, -7, -8, -9, -10,
                        -11, -12, -13, -14, -15, -16, -17, -18, -19, -20},
                    new int[]{
                        19, 18, 17, 16, 15, 14, 13, 12, 11, 10,
                        8, 6, 4, 2, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                ),

                new TestItem("Aged Brie",
                    new int[]{
                        1, 0, -1, -2, -3, -4, -5, -6, -7, -8,
                        -9, -10, -11, -12, -13, -14, -15, -16, -17, -18,
                        -19, -20, -21, -22, -23, -24, -25, -26, -27, -28},
                    new int[]{
                        1, 2, 4, 6, 8, 10, 12, 14, 16, 18,
                        20, 22, 24, 26, 28, 30, 32, 34, 36, 38,
                        40, 42, 44, 46, 48, 50, 50, 50, 50, 50}
                ),

                new TestItem("Elixir of the Mongoose",
                    new int[]{
                        4, 3, 2, 1, 0, -1, -2, -3, -4, -5,
                        -6, -7, -8, -9, -10, -11, -12, -13, -14, -15,
                        -16, -17, -18, -19, -20, -21, -22, -23, -24, -25},
                    new int[]{
                        6, 5, 4, 3, 2, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                ),

                new TestItem("Sulfuras, Hand of Ragnaros",
                    new int[]{
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    new int[]{
                        80, 80, 80, 80, 80, 80, 80, 80, 80, 80,
                        80, 80, 80, 80, 80, 80, 80, 80, 80, 80,
                        80, 80, 80, 80, 80, 80, 80, 80, 80, 80}
                ),

                new TestItem("Sulfuras, Hand of Ragnaros",
                    new int[]{
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    new int[]{
                        80, 80, 80, 80, 80, 80, 80, 80, 80, 80,
                        80, 80, 80, 80, 80, 80, 80, 80, 80, 80,
                        80, 80, 80, 80, 80, 80, 80, 80, 80, 80}
                ),

                new TestItem("Backstage passes to a TAFKAL80ETC concert",
                    new int[]{
                        14, 13, 12, 11, 10, 9, 8, 7, 6, 5,
                        4, 3, 2, 1, 0, -1, -2, -3, -4, -5,
                        -6, -7, -8, -9, -10, -11, -12, -13, -14, -15},
                    new int[]{
                        21, 22, 23, 24, 25, 27, 29, 31, 33, 35,
                        38, 41, 44, 47, 50, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                ),

                new TestItem("Backstage passes to a TAFKAL80ETC concert",
                    new int[]{
                        9, 8, 7, 6, 5, 4, 3, 2, 1, 0,
                        -1, -2, -3, -4, -5, -6, -7, -8, -9, -10,
                        -11, -12, -13, -14, -15, -16, -17, -18, -19, -20},
                    new int[]{
                        50, 50, 50, 50, 50, 50, 50, 50, 50, 50,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                ),

                new TestItem("Backstage passes to a TAFKAL80ETC concert",
                    new int[]{
                        4, 3, 2, 1, 0, -1, -2, -3, -4, -5,
                        -6, -7, -8, -9, -10, -11, -12, -13, -14, -15,
                        -16, -17, -18, -19, -20, -21, -22, -23, -24, -25},
                    new int[]{
                        50, 50, 50, 50, 50, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                ),

                new TestItem("Conjured Mana Cake",
                    new int[]{
                        2, 1, 0, -1, -2, -3, -4, -5, -6, -7,
                        -8, -9, -10, -11, -12, -13, -14, -15, -16, -17,
                        -18, -19, -20, -21, -22, -23, -24, -25, -26, -27},
                    new int[]{
                        5, 4, 3, 1, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                )
            )));
    }

    private record TestItem(String name, int[] sellInArray, int[] qualityArray) {
    }
}
