package com.tompy.directive;

/**
 * Represents the thing that is done during an event
 */
public enum ActionType {
    ACTION_DESCRIBE("describe"), ACTION_ENCOUNTER("encounter"), ACTION_EXPLORE("explore"),
    ACTION_END_ADVENTURE("end"), ACTION_MAKE_VISIBLE("visible"), ACTION_ADD_EVENT("add_event"),
    ACTION_REMOVE_EVENT("remove_event"), ACTION_SEND_TO_AREA("send_to_area"), ACTION_ACTOR_MOVE("actor_move");

    /**
     * Short description of the action
     */
    private String description;

    ActionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
