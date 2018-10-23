package com.tompy.entity.event;

import com.tompy.directive.EventType;

import java.util.List;
import java.util.Set;

/**
 * Manages events for Event Types
 */
public interface EventManager {
    /**
     * Clear the managed Events
     */
    void clear();

    /**
     * Add an event of a certain type
     *
     * @param type
     * @param event
     */
    void add(EventType type, Event event);

    /**
     * Remove an event of a certain type
     *
     * @param type
     * @param event
     */
    void remove(EventType type, Event event);

    /**
     * Remove all occurences of a specific Event
     *
     * @param event
     */
    void remove(Event event);

    /**
     * Retrieve all unique events
     *
     * @return
     */
    Set<Event> getAll();

    /**
     * Get all events of a certain type
     *
     * @param type
     * @return
     */
    List<Event> getAllOfType(EventType type);
}
