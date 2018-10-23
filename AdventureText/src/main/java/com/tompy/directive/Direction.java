package com.tompy.directive;

/**
 * A list of possible directions
 */
public enum Direction {
    DIRECTION_NORTH("North"), DIRECTION_EAST("East"), DIRECTION_SOUTH("South"), DIRECTION_WEST("West");

    /**
     * The printable description
     */
    private String description;

    Direction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
