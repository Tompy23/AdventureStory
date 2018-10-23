package com.tompy.entity.event;

public interface EventBuilderFactory {
    EventBuilder createEventBuilder();

    void addEvent(Event event);
}
