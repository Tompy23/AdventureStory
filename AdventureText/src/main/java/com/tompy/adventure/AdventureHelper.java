package com.tompy.adventure;

import com.tompy.attribute.Attribute;
import com.tompy.common.Coordinates;
import com.tompy.directive.*;
import com.tompy.entity.Actor.Actor;
import com.tompy.entity.Actor.MoveStrategyFactory;
import com.tompy.entity.Entity;
import com.tompy.entity.EntityService;
import com.tompy.entity.area.Area;
import com.tompy.entity.encounter.Encounter;
import com.tompy.entity.encounter.EncounterBuilder;
import com.tompy.entity.event.Event;
import com.tompy.entity.event.EventBuilder;
import com.tompy.entity.feature.Feature;
import com.tompy.entity.feature.FeatureBuilder;
import com.tompy.entity.item.Item;
import com.tompy.entity.item.ItemBuilder;
import com.tompy.exit.Exit;
import com.tompy.exit.ExitBuilderFactory;
import com.tompy.messages.MessageHandler;
import com.tompy.player.Player;
import com.tompy.state.AdventureStateFactory;

import java.util.Objects;

import static com.tompy.directive.ActionType.ACTION_DESCRIBE;
import static com.tompy.directive.EventType.EVENT_ACTOR_PROGRAM;
import static com.tompy.directive.TriggerType.TRIGGER_ALWAYS;
import static com.tompy.directive.TriggerType.TRIGGER_VISIBLE;

/**
 * Helper class for building Adventure
 */
public abstract class AdventureHelper {
    protected final Player player;
    protected final EntityService entityService;
    protected final ExitBuilderFactory exitBuilderFactory;
    protected final MessageHandler messages;
    protected AdventureStateFactory stateFactory;
    protected MoveStrategyFactory moveStrategyFactory;
    private Adventure thisAdventure;

    public AdventureHelper(Player player, EntityService entityService, ExitBuilderFactory exitBuilderFactory) {
        this.player = Objects.requireNonNull(player, "Player cannot be null.");
        this.entityService = Objects.requireNonNull(entityService, "Entity Service cannot be null.");
        this.exitBuilderFactory = exitBuilderFactory;
        this.messages = new MessageHandler();
    }

    protected void setThisAdventure(Adventure adventure) {
        this.thisAdventure = Objects.requireNonNull(adventure, "This Adventure cannot be null.");
    }


    /**
     * Translate responses from message id
     *
     * @param responses
     * @return
     */
    protected String[] fixResponses(String... responses) {
        String[] fixedResponses = new String[responses.length];
        for (int i = 0; i < responses.length; i++) {
            fixedResponses[i] = messages.get(responses[i]);
        }
        return fixedResponses;
    }

    /**
     * Area
     *
     * @param name
     * @return
     */
    protected Area buildArea(String name, Coordinates coordinates) {
        Area area = entityService.createAreaBuilder().name(name).coordinates(coordinates).build();
        entityService.addArea(area);
        return area;
    }

    /**
     * Exit
     *
     * @param a1
     * @param d1
     * @param a2
     * @param d2
     * @param open
     * @return
     */
    protected Exit buildExit(Area a1, Direction d1, Area a2, Direction d2, boolean open, int ticks) {
        Exit e = exitBuilderFactory.builder().area(a1).area(a2).state(open).passThruTicks(ticks).build();
        a1.installExit(d1, e);
        a2.installExit(d2, e);
        return e;
    }

    /**
     * Basic Feature
     *
     * @param type
     * @param description
     * @return
     */
    protected Feature buildFeature(FeatureType type, String description) {
        Feature feature = featureBuilder(type, description).build();
        entityService.addFeature(feature);
        return feature;
    }

    /**
     * Named Feature
     *
     * @param type
     * @param description
     * @param name
     * @return
     */
    protected Feature buildFeature(FeatureType type, String description, String name) {
        Feature feature = featureBuilder(type, description).name(name).build();
        entityService.addFeature(feature);
        return feature;
    }

    /**
     * Feature Builder
     *
     * @param type
     * @param description
     * @return
     */
    protected FeatureBuilder featureBuilder(FeatureType type, String description) {
        return entityService.createFeatureBuilder().type(type).description(messages.get(description));
    }

    /**
     * Event Builder
     *
     * @param action
     * @param trigger
     * @param entity
     * @param responses
     * @return
     */
    protected EventBuilder eventBuilder(ActionType action, TriggerType trigger, Entity entity, String... responses) {
        return entityService.createEventBuilder().actionType(action).triggerType(trigger).entity(entity)
                .responses(fixResponses(responses)).stateFactory(stateFactory);
    }

    /**
     *
     * @param entity
     * @param type
     * @param responses
     */
    protected void describeAlways(Entity entity, EventType type, String... responses) {
        addEvent(entity, type, eventBuilder(ACTION_DESCRIBE, TRIGGER_ALWAYS, entity, responses).build());
    }

    protected void describeVisible(Entity entity, EventType type, String... responses) {
        addEvent(entity, type, eventBuilder(ACTION_DESCRIBE, TRIGGER_VISIBLE, entity, responses).build());
    }

    /**
     * Add Event to Entity
     *
     * @param entity
     * @param type
     * @param event
     */
    protected void addEvent(Entity entity, EventType type, Event event) {
        entityService.add(entity, type, event);
    }

    /**
     * Add an attribute to an entity
     *
     * @param e
     * @param a
     */
    protected void add(Entity e, Attribute a) {
        entityService.add(e, a);
    }

    /**
     * Add an attribute and value to an entity
     *
     * @param e
     * @param a
     * @param v
     */
    protected void add(Entity e, Attribute a, int v) {
        entityService.add(e, a, v);
    }

    /**
     * Remove an attribute from an entity
     *
     * @param e
     * @param a
     */
    protected void remove(Entity e, Attribute a) {
        entityService.remove(e, a);
    }

    /**
     * Encounter
     *
     * @param t
     * @return
     */
    protected Encounter buildEncounter(EncounterType t) {
        Encounter encounter = encounterBuilder(t).build();
        entityService.addEncounter(encounter);
        return encounter;
    }

    protected EncounterBuilder encounterBuilder(EncounterType t) {
        return entityService.createEncounterBuilder(player, thisAdventure).type(t);
    }

    /**
     * Item
     *
     * @param t
     * @param d
     * @return
     */
    protected Item buildItem(ItemType t, String d) {
        Item item = itemBuilder(t, d).build();
        entityService.addItem(item);
        return item;
    }

    /**
     * Item Builder
     *
     * @param t
     * @param d
     * @return
     */
    protected ItemBuilder itemBuilder(ItemType t, String d) {
        return entityService.createItemBuilder().type(t).description(messages.get(d));
    }

    /**
     * Actor
     *
     * @param name
     * @param description
     * @return
     */
    protected Actor buildActor(String name, String description, MoveStrategyType moveType) {
        Actor actor = entityService.createActorBuilder().name(name).description(description).build();
        actor.assignMoveStrategy(moveStrategyFactory.createMoveStrategy(actor, moveType));
        entityService.addActor(actor);
        return actor;
    }

    /**
     * Add an event to an actor
     *
     * @param actor
     * @param event
     */
    protected void programActor(Actor actor, Event event) {
        addEvent(actor, EVENT_ACTOR_PROGRAM, event);
    }
}
