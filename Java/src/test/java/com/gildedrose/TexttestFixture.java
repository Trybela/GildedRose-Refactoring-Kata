package com.gildedrose;

import com.gildedrose.lifecycle.ItemLifecycleUpdater;
import com.gildedrose.model.Item;

public class TexttestFixture {
    public static void main(String[] args) {
        System.out.println("OMGHAI!");

        Item[] items = SetUpHelperTest.setUpItems();

        ItemLifecycleUpdater app = new ItemLifecycleUpdater(items);

        int days = 2;
        if (args.length > 0) {
            days = Integer.parseInt(args[0]) + 1;
        }

        for (int i = 0; i < days; i++) {
            System.out.println("-------- day " + i + " --------");
            System.out.println("name, sellIn, quality");
            for (Item item : items) {
                System.out.println(item);
            }
            System.out.println();
            app.processDailyChange();
        }
    }

}
