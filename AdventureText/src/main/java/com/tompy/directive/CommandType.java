package com.tompy.directive;

import com.tompy.response.Response;

/**
 * Command types for executions
 */
public enum CommandType {
    COMMAND_CLOSE("Close", "Closing"), COMMAND_DROP("Drop", "Dropping"), // TODO
    COMMAND_DROP_IN("Drop", "Dropping"), // TODO
    COMMAND_DROP_ON("Drop", "Dropping"), // TODO
    COMMAND_EQUIP("Equip", "Equipping"), // TODO (Must be in inventory)
    COMMAND_INSPECT_ITEM("Inspect", "Inspecting"), // TODO (Must be in inventory)
    COMMAND_INVENTORY("Inventory", "Showing inventory"), COMMAND_MOVE("Move", "Moving"), COMMAND_NULL("", ""),
    COMMAND_OPEN("Open", "Opening"), COMMAND_QUIT("Quit", "Quitting"), COMMAND_RUN("Run", "Running"), // TODO
    COMMAND_SEARCH("Search", "Searching"), COMMAND_SEARCH_DIRECTION("Search", "Searching"),
    COMMAND_SEARCH_FEATURE("Search", "Searching"), COMMAND_SEARCH_IN("Search in", "Searching in"), // TODO
    COMMAND_SEARCH_ON("Search on", "Searching on"), // TODO
    COMMAND_TAKE("Take", "Taking"), COMMAND_TAKE_FROM("Take from", "Taking from"), COMMAND_TALK("Talk", "Talking"),
    COMMAND_UNEQUIP("Unequip", "Unequipping"), // TODO
    COMMAND_USE("Use", "Using");

    /**
     * A printable name for the command
     */
    private String description;

    /**
     * A participle version of the command to help with creating {@link Response}
     */
    private String participle;

    CommandType(String description, String participle) {
        this.description = description;
        this.participle = participle;
    }

    public String getDescription() {
        return description;
    }

    public String getParticiple() {
        return participle;
    }
}
