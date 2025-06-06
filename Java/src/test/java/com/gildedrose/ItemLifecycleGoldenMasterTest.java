package com.gildedrose;

import com.gildedrose.lifecycle.ItemLifecycleUpdater;
import com.gildedrose.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.gildedrose.constant.ItemsNameConstant.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemLifecycleGoldenMasterTest {

    private Item[] items;

    @BeforeEach
    void setUpItems() {
        this.items = SetUpHelperTest.setUpItems();
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
     *
     * @return number of days and a list of items with their expected sell-in and quality values for
     * each day consequentially.
     */
    private static Stream<Arguments> expectedItemValuesToDayProvider() {
        return Stream.of(
            Arguments.of(30, List.of(
                new TestItem(DEXTERITY_VEST,
                    new int[]{
                        9, 8, 7, 6, 5, 4, 3, 2, 1, 0,
                        -1, -2, -3, -4, -5, -6, -7, -8, -9, -10,
                        -11, -12, -13, -14, -15, -16, -17, -18, -19, -20},
                    new int[]{
                        19, 18, 17, 16, 15, 14, 13, 12, 11, 10,
                        8, 6, 4, 2, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                ),

                new TestItem(AGED_BRIE,
                    new int[]{
                        1, 0, -1, -2, -3, -4, -5, -6, -7, -8,
                        -9, -10, -11, -12, -13, -14, -15, -16, -17, -18,
                        -19, -20, -21, -22, -23, -24, -25, -26, -27, -28},
                    new int[]{
                        1, 2, 4, 6, 8, 10, 12, 14, 16, 18,
                        20, 22, 24, 26, 28, 30, 32, 34, 36, 38,
                        40, 42, 44, 46, 48, 50, 50, 50, 50, 50}
                ),

                new TestItem(ELIXIR_OF_THE_MONGOOSE,
                    new int[]{
                        4, 3, 2, 1, 0, -1, -2, -3, -4, -5,
                        -6, -7, -8, -9, -10, -11, -12, -13, -14, -15,
                        -16, -17, -18, -19, -20, -21, -22, -23, -24, -25},
                    new int[]{
                        6, 5, 4, 3, 2, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                ),

                new TestItem(SULFURAS,
                    new int[]{
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    new int[]{
                        80, 80, 80, 80, 80, 80, 80, 80, 80, 80,
                        80, 80, 80, 80, 80, 80, 80, 80, 80, 80,
                        80, 80, 80, 80, 80, 80, 80, 80, 80, 80}
                ),

                new TestItem(SULFURAS,
                    new int[]{
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    new int[]{
                        80, 80, 80, 80, 80, 80, 80, 80, 80, 80,
                        80, 80, 80, 80, 80, 80, 80, 80, 80, 80,
                        80, 80, 80, 80, 80, 80, 80, 80, 80, 80}
                ),

                new TestItem(BACKSTAGE_PASSES,
                    new int[]{
                        14, 13, 12, 11, 10, 9, 8, 7, 6, 5,
                        4, 3, 2, 1, 0, -1, -2, -3, -4, -5,
                        -6, -7, -8, -9, -10, -11, -12, -13, -14, -15},
                    new int[]{
                        21, 22, 23, 24, 25, 27, 29, 31, 33, 35,
                        38, 41, 44, 47, 50, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                ),

                new TestItem(BACKSTAGE_PASSES,
                    new int[]{
                        9, 8, 7, 6, 5, 4, 3, 2, 1, 0,
                        -1, -2, -3, -4, -5, -6, -7, -8, -9, -10,
                        -11, -12, -13, -14, -15, -16, -17, -18, -19, -20},
                    new int[]{
                        50, 50, 50, 50, 50, 50, 50, 50, 50, 50,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                ),

                new TestItem(BACKSTAGE_PASSES,
                    new int[]{
                        4, 3, 2, 1, 0, -1, -2, -3, -4, -5,
                        -6, -7, -8, -9, -10, -11, -12, -13, -14, -15,
                        -16, -17, -18, -19, -20, -21, -22, -23, -24, -25},
                    new int[]{
                        50, 50, 50, 50, 50, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                ),

                new TestItem(CONJURED_MANA_CAKE,
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
