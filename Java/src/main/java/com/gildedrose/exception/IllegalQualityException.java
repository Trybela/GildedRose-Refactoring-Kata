package com.gildedrose.exception;

import com.gildedrose.model.Item;

public class IllegalQualityException extends ItemUpdateException {

    public IllegalQualityException(Item item) {
        super("Invalid quality value for item: " + item);
    }
}
