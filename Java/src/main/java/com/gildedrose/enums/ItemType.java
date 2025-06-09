package com.gildedrose.enums;

import com.gildedrose.model.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import static com.gildedrose.constant.ItemsNameConstant.*;
import static java.util.stream.Collectors.toMap;

@Getter
@RequiredArgsConstructor
public enum ItemType {

    AGED_BRIE(AGED_BRIE_NAME, AgedBrieItem::new),
    BACKSTAGE_PASS(BACKSTAGE_PASSES_NAME, BackstagePassItem::new),
    CONJURED(CONJURED_MANA_CAKE_NAME, ConjuredItem::new),
    SULFURAS(SULFURAS_NAME, SulfurasItem::new),
    DEFAULT("*", DefaultItem::new);

    private final String name;
    private final Function<Item, AbstractItem> abstractItemFunction;

    private static final Map<String, ItemType> ITEM_RESOLVER =
        Arrays.stream(values())
            .collect(toMap(ItemType::getName, Function.identity()));

    public static AbstractItem applyFromItem(Item rawItem) {
        ItemType itemType = ITEM_RESOLVER.getOrDefault(rawItem.name, DEFAULT);
        return itemType.abstractItemFunction.apply(rawItem);
    }
}
