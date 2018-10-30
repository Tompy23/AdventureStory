package com.tompy.entity;

import com.tompy.adventure.Adventure;
import com.tompy.attribute.Attribute;
import com.tompy.attribute.AttributeManager;
import com.tompy.directive.EventType;
import com.tompy.entity.Actor.Actor;
import com.tompy.entity.Actor.ActorBuilderFactory;
import com.tompy.entity.area.Area;
import com.tompy.entity.area.AreaBuilderFactory;
import com.tompy.entity.encounter.Encounter;
import com.tompy.entity.encounter.EncounterBuilderFactory;
import com.tompy.entity.event.Event;
import com.tompy.entity.event.EventBuilderFactory;
import com.tompy.entity.event.EventManager;
import com.tompy.entity.feature.Feature;
import com.tompy.entity.feature.FeatureBuilderFactory;
import com.tompy.entity.item.Item;
import com.tompy.entity.item.ItemBuilderFactory;
import com.tompy.player.Player;
import com.tompy.response.Response;

import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

/**
 * A service combining entities with various functions and states via an Attribute Manager
 */
public interface EntityService
        extends ItemBuilderFactory, FeatureBuilderFactory, AreaBuilderFactory, EventBuilderFactory,
        EncounterBuilderFactory, ActorBuilderFactory {

    /**
     * Add Attribute to an entity
     *
     * @param key       - The entity
     * @param attribute - Attribute to add
     * @return - A reference to itself for chaining
     */
    EntityService add(Entity key, Attribute attribute);

    /**
     * Add Attribute to an entity with a starting value
     *
     * @param key       - The entity
     * @param attribute - Attribute to add
     * @param value     - Starting value
     * @return - A reference to itself for chaining
     */
    EntityService add(Entity key, Attribute attribute, Integer value);

    /**
     * Remove an Attribute from an entity
     *
     * @param key       - The entity
     * @param attribute - Attribute to remove
     */
    void remove(Entity key, Attribute attribute);

    /**
     * Reset an Attribute's value for an entity
     *
     * @param key       - The entity
     * @param attribute - Attribute to reset
     */
    void reset(Entity key, Attribute attribute);

    /**
     * Determine if this entity has this attribute
     *
     * @param key       - The entity
     * @param attribute - Attribute to check
     * @return - True if the entity has the Attribute
     */
    boolean is(Entity key, Attribute attribute);

    /**
     * Retreive the value for an entity if it has one
     *
     * @param key       - The entity
     * @param attribute - Attribute with the value
     * @return - An Optional with the value or an empty Optional if no value for the Attribute
     */
    OptionalInt valueFor(Entity key, Attribute attribute);

    /**
     * Retrieve entity by its name
     *
     * @param name
     * @return
     */
    Entity getEntityByName(String name);

    Item getItemByDescription(String description);

    Feature getFeatureByDescription(String description);

    /**
     * Retrieve a specific area by its name
     *
     * @param name
     * @return
     */
    Area getAreaByName(String name);

    String getAttributeDescription(Entity key, Attribute attribute);


    // This group of functions represents the ability to build an item's attribute with attribute
    // specific responses based on whether the attribute exists or not.
    void addAttributeDoesApply(Entity key, Attribute attribute, String text);

    void addAttributeDoesNotApply(Entity key, Attribute attribute, String text);

    String getAttributeApplicationText(Entity key, Attribute attribute);

    // These are functions associated with Event Managers

    /**
     * Associate an event with an entity and one of its event types.
     * @param entity
     * @param type
     * @param event
     * @return
     */
    EntityService add(Entity entity, EventType type, Event event);

    /**
     * Remove an event from an entity of a certain type
     *
     * @param entity
     * @param type
     * @param event
     */
    void remove(Entity entity, EventType type, Event event);

    /**
     * Get a list of all events for an entity and a type
     *
     * @param entity
     * @param type
     * @return
     */
    List<Event> get(Entity entity, EventType type);

    /**
     * Handle the events for an entity and type
     *
     * @param entity
     * @param type
     * @param player
     * @param adventure
     * @return
     */
    List<Response> handle(Entity entity, EventType type, Player player, Adventure adventure);

    /**
     * Return a list of all Actors in the adventure
     *
     * @return List of actors
     */
    List<Actor> getActors();

    List<Item> getItems();

    List<Feature> getFeatures();

    List<Encounter> getEncounters();

    List<Area> getAreas();

    List<Event> getEvents();

    Long getEntityKey();

    Map<String, Entity> getEntityMap();

    Map<Long, EventManager> getEventManagers();

    Map<Long, AttributeManager> getAttributeManagers();
}
