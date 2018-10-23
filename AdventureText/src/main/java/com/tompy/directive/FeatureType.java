package com.tompy.directive;

/**
 * Features are immovable things in an Area that can have interaction
 */
public enum FeatureType {
    FEATURE_BASIC("Feature"), FEATURE_CHEST("Chest"), FEATURE_DOOR("Door"), FEATURE_MONSTER("Monster"),
    FEATURE_TABLE("Table");

    /**
     * Short description
     */
    private String description;

    FeatureType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
