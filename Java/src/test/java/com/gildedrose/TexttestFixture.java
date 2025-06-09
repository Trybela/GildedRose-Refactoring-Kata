package com.gildedrose;

import com.gildedrose.lifecycle.ItemLifecycleUpdater;
import com.gildedrose.model.Item;

import java.util.List;

public final class TexttestFixture {

    private static final int DEFAULT_SIMULATION_DAYS = 2;

    private TexttestFixture() {}

    public static void main(String[] args) {
        System.out.println("OMGHAI!");

        List<Item> initialInventory = SetUpHelperTest.setUpItems();
        Item[] inventoryArray = initialInventory.toArray(new Item[0]);
        var updater = new ItemLifecycleUpdater();

        int days = parseDaysArgument(args);

        simulate(days, inventoryArray, updater);
    }

    private static int parseDaysArgument(String[] args) {
        if (args.length == 0) {
            return DEFAULT_SIMULATION_DAYS;
        }
        try {
            int parsed = Integer.parseInt(args[0]);
            return parsed < 0 ? DEFAULT_SIMULATION_DAYS : parsed + 1;
        } catch (NumberFormatException ex) {
            System.err.printf(
                    "Cannot parse '%s' as an integer. Falling back to default of %d days.%n",
                    args[0], DEFAULT_SIMULATION_DAYS);
            return DEFAULT_SIMULATION_DAYS;
        }
    }

    private static void simulate(int days, Item[] inventory, ItemLifecycleUpdater updater) {
        for (int day = 0; day < days; day++) {
            printInventoryHeader(day);
            for (Item item : inventory) {
                System.out.println(item);
            }
            System.out.println();

            try {
                updater.processDailyChange(List.of(inventory));
            } catch (Exception e) {
                System.err.printf("Inventory update failed on day %d: %s%n", day, e.getMessage());
                break;
            }
        }
    }

    private static void printInventoryHeader(int day) {
        System.out.printf("-------- day %d --------%n", day);
        System.out.println("name, sellIn, quality");
    }
}
