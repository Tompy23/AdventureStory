package com.tompy.entity.event;

import com.tompy.common.Builder;
import com.tompy.directive.Direction;
import com.tompy.directive.ActionType;
import com.tompy.directive.EventType;
import com.tompy.directive.TriggerType;
import com.tompy.entity.Actor.Actor;
import com.tompy.entity.Entity;
import com.tompy.entity.area.Area;
import com.tompy.entity.encounter.Encounter;
import com.tompy.state.AdventureStateFactory;

import java.util.List;

public interface EventBuilder extends Builder<Event> {
    EventBuilder name(String name);

    EventBuilder memo(String memo);

    EventBuilder actionType(ActionType actionType);

    EventBuilder triggerType(TriggerType triggerType);

    EventBuilder stateFactory(AdventureStateFactory stateFactory);

    EventBuilder entity(Entity entity);

    EventBuilder responses(String... responses);

    EventBuilder text(String text);

    EventBuilder direction(Direction direction);

    EventBuilder encounter(Encounter encounter);

    EventBuilder delay(int delay);

    EventBuilder events(List<Event> events);

    EventBuilder area(Area area);

    EventBuilder actor(Actor actor);

    EventBuilder eventType(EventType subType);
}
