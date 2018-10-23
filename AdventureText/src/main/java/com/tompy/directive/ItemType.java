package com.tompy.directive;

/**
 * A thing that can be added to a player's inventory and Used.
 */
public enum ItemType {
    ITEM_TEST("test"), ITEM_GEM("Gem"), ITEM_KEY("Key"), ITEM_WEAPON("Weapon"), ITEM_POTION("Potion");

    /**
     * Short description
     */
    private String description;

    ItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
