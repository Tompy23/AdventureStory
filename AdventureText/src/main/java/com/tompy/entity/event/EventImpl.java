package com.tompy.entity.event;

import com.tompy.adventure.Adventure;
import com.tompy.directive.ActionType;
import com.tompy.directive.Direction;
import com.tompy.directive.EventType;
import com.tompy.directive.TriggerType;
import com.tompy.entity.Actor.ActionActorMove;
import com.tompy.entity.Actor.Actor;
import com.tompy.entity.Entity;
import com.tompy.entity.EntityService;
import com.tompy.entity.area.Area;
import com.tompy.entity.encounter.Encounter;
import com.tompy.entity.EntityBuilderHelperImpl;
import com.tompy.entity.EntityImpl;
import com.tompy.player.Player;
import com.tompy.response.Response;
import com.tompy.state.AdventureStateFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class EventImpl extends EntityImpl implements Event {
    public static final Logger LOGGER = LogManager.getLogger(EventImpl.class);
    private final Action action;
    private final Trigger trigger;

    public EventImpl(Long key, String name, List<String> descriptors, String description, EntityService entityService,
            Action action, Trigger trigger) {
        super(key, name, descriptors, description, entityService);
        this.action = Objects.requireNonNull(action, "Action cannot be null.");
        this.trigger = Objects.requireNonNull(trigger, "Trigger cannot be null.");
    }

    public static EventBuilder createBuilder(Long key, EntityService entityService) {
        return new EventBuilderImpl(key, entityService);
    }

    @Override public boolean pull(Player player, Adventure adventure, EntityService entityService) {
        return trigger.pull(player, adventure, entityService);
    }

    @Override public List<Response> apply(Player player, Adventure adventure) {
        return action.apply(player, adventure);
    }

    public static final class EventBuilderImpl extends EntityBuilderHelperImpl implements EventBuilder {
        private ActionType actionType;
        private TriggerType triggerType;
        private Entity entity;
        private String[] responses;
        private String text;
        private Direction direction;
        private Encounter encounter;
        private AdventureStateFactory stateFactory;
        private int delay;
        private List<Event> events;
        private Area area;
        private Actor actor;
        private EventType subEventType;

        public EventBuilderImpl(Long key, EntityService entityService) {
            super(key, entityService);
        }

        @Override public EventBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override public EventBuilder memo(String memo) {
            this.description = memo;
            return this;
        }

        @Override public EventBuilder actionType(ActionType actionType) {
            this.actionType = actionType;
            return this;
        }

        @Override public EventBuilder triggerType(TriggerType triggerType) {
            this.triggerType = triggerType;
            return this;
        }

        @Override public EventBuilder stateFactory(AdventureStateFactory stateFactory) {
            this.stateFactory = stateFactory;
            return this;
        }

        @Override public EventBuilder entity(Entity entity) {
            this.entity = entity;
            return this;
        }

        @Override public EventBuilder responses(String... responses) {
            this.responses = responses;
            return this;
        }

        @Override public EventBuilder text(String text) {
            this.text = text;
            return this;
        }

        @Override public EventBuilder direction(Direction direction) {
            this.direction = direction;
            return this;
        }

        @Override public EventBuilder encounter(Encounter encounter) {
            this.encounter = encounter;
            return this;
        }

        @Override public EventBuilder delay(int delay) {
            this.delay = delay;
            return this;
        }

        @Override
        public EventBuilder events(List<Event> events) {
            this.events = Collections.unmodifiableList(events);
            return this;
        }

        @Override
        public EventBuilder eventType(EventType subType) {
            this.subEventType = subType;
            return this;
        }

        @Override
        public EventBuilder area(Area area) {
            this.area = area;
            return this;
        }

        @Override
        public EventBuilder actor(Actor actor) {
            this.actor = actor;
            return this;
        }

        @Override public Event build() {
            LOGGER.info("Building event [{}]", key);
            return new EventImpl(key, name, this.buildDescriptors(), description, entityService, buildAction(),
                    buildTrigger());
        }

        private Action buildAction() {
            switch (actionType) {
                case ACTION_ADD_EVENT:
                    return new ActionAddEventImpl(entity, entityService, responses, subEventType, events);
                case ACTION_DESCRIBE:
                    return new ActionDescribeImpl(entity, entityService, responses);
                case ACTION_ENCOUNTER:
                    return new ActionEncounterImpl(entity, entityService, responses, encounter, stateFactory);
                case ACTION_EXPLORE:
                    return new ActionExploreImpl(entity, entityService, responses, stateFactory);
                case ACTION_END_ADVENTURE:
                    return new ActionDeathImpl(entity, entityService, responses);
                case ACTION_MAKE_VISIBLE:
                    return new ActionVisibleImp(entity, entityService, responses);
                case ACTION_REMOVE_EVENT:
                    return new ActionRemoveEvent(entity, entityService, responses, subEventType, events);
                case ACTION_SEND_TO_AREA:
                    return new ActionSendImpl(entity, entityService, responses, area, direction);
                case ACTION_ACTOR_MOVE:
                    return new ActionActorMove(entity, entityService, responses, actor);
            }
            return null;
        }

        private Trigger buildTrigger() {
            switch (triggerType) {
                case TRIGGER_ALWAYS:
                    return new TriggerAlwaysImpl(entity);
                case TRIGGER_ONCE:
                    return new TriggerOnceImpl(entity);
                case TRIGGER_ONCE_DELAY:
                    return new TriggerOnceAfterDelay(entity, delay);
                case TRIGGER_ALWAYS_DELAY:
                    return new TriggerAlwaysAfterDelay(entity, delay);
                case TRIGGER_VISIBLE:
                    return new TriggerVisibleImpl(entity);
            }
            return null;
        }
    }
}
