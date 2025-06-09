package com.gildedrose.lifecycle;

import com.gildedrose.enums.ItemType;
import com.gildedrose.exeption.ItemUpdateException;
import com.gildedrose.model.AbstractItem;
import com.gildedrose.model.Item;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
public class ItemLifecycleUpdater {

    public void processDailyChange(List<Item> items) {
        log.debug("Starting processing of daily change for the following items: {}", items);

        items.stream()
            .map(ItemType::applyFromItem)
            .forEach(this::processItemDailyChange);

        log.debug("Finished processing of daily change.");
    }

    private void processItemDailyChange(AbstractItem abstractItem) {
        log.debug("Starting processing of daily change for the item: {}", abstractItem);

        try {
            abstractItem.update();
            log.debug("Finished processing of daily change. Result item: {}", abstractItem);
        } catch (Exception ex) {
            log.error("Failed to update item {} - skipping. Root cause: {}", abstractItem, ex, ex);
            throw new ItemUpdateException("Failed to update item"  + abstractItem, ex);
        }
    }
}
