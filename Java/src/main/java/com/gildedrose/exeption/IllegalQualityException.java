package com.gildedrose.exeption;

import com.gildedrose.model.Item;

public class IllegalQualityException extends RuntimeException {

    public IllegalQualityException(Item item) {
        super("Invalid quality value for item: " + item);
    }
}
