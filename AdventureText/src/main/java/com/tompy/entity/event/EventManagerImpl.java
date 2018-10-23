package com.tompy.entity.event;

import com.tompy.directive.EventType;
import com.tompy.entity.event.Event;
import com.tompy.entity.event.EventManager;

import java.util.*;


public class EventManagerImpl implements EventManager {
    Map<EventType, List<Event>> managed;

    public EventManagerImpl() {
        managed = new HashMap<>();
    }

    @Override
    public void clear() {
        managed.clear();
    }

    @Override
    public void add(EventType type, Event event) {
        if (!managed.containsKey(type)) {
            managed.put(type, new ArrayList<>());
        }
        managed.get(type).add(event);
    }

    @Override
    public void remove(EventType type, Event event) {
        if (managed.containsKey(type)) {
            managed.get(type).remove(event);
        }
    }

    @Override
    public void remove(Event event) {
        for (List<Event> events : managed.values()) {
            events.remove(event);
        }
    }

    @Override
    public Set<Event> getAll() {
        Set<Event> returnValue = new HashSet<>();
        for (List<Event> events : managed.values()) {
            returnValue.addAll(events);
        }
        return returnValue;
    }

    @Override
    public List<Event> getAllOfType(EventType type) {
        if (managed.containsKey(type)) {
            return managed.get(type);
        }
        return Collections.emptyList();
    }
}
