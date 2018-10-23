package com.tompy.directive;

/**
 * Whether or not an Event happens
 */
public enum TriggerType {
    TRIGGER_ALWAYS("always"), TRIGGER_ONCE("once"), TRIGGER_ONCE_DELAY("once_delay"),
    TRIGGER_ALWAYS_DELAY("always_delay");

    /**
     * Short description
     */
    private String description;

    TriggerType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
