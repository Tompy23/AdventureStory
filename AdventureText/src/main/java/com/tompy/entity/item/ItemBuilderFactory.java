package com.tompy.entity.item;

/**
 * Functional Interface for building an Item Builder
 */
public interface ItemBuilderFactory {

    /**
     * Creates a new Item Builder
     *
     * @return - the builder
     */
    ItemBuilder createItemBuilder();

    void addItem(Item item);
}
