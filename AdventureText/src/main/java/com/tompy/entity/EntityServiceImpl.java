package com.tompy.entity;

import com.tompy.adventure.Adventure;
import com.tompy.attribute.Attribute;
import com.tompy.attribute.AttributeManager;
import com.tompy.attribute.AttributeManagerFactory;
import com.tompy.directive.EventType;
import com.tompy.entity.Actor.Actor;
import com.tompy.entity.Actor.ActorBuilder;
import com.tompy.entity.Actor.ActorImpl;
import com.tompy.entity.area.Area;
import com.tompy.entity.area.AreaBuilder;
import com.tompy.entity.area.AreaImpl;
import com.tompy.entity.encounter.Encounter;
import com.tompy.entity.encounter.EncounterBuilder;
import com.tompy.entity.encounter.EncounterImpl;
import com.tompy.entity.event.Event;
import com.tompy.entity.event.EventBuilder;
import com.tompy.entity.event.EventManager;
import com.tompy.entity.event.EventManagerFactory;
import com.tompy.entity.event.EventImpl;
import com.tompy.entity.feature.Feature;
import com.tompy.entity.feature.FeatureBuilder;
import com.tompy.entity.feature.FeatureBasicImpl;
import com.tompy.entity.item.Item;
import com.tompy.entity.item.ItemBuilder;
import com.tompy.entity.item.ItemImpl;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class EntityServiceImpl implements EntityService {
    private static final Logger LOGGER = LogManager.getLogger(EntityServiceImpl.class);
    private final AttributeManagerFactory attributeManagerFactory;
    private final EventManagerFactory eventManagerFactory;
    private Map<Long, AttributeManager> attributeManagers;
    private Map<Long, EventManager> eventManagers;
    private List<Item> items;
    private List<Feature> features;
    private List<Area> areas;
    private List<Event> events;
    private List<Encounter> encounters;
    private List<Actor> actors;
    private Long entityKey;
    private Map<String, Entity> entityMap;

    public EntityServiceImpl(AttributeManagerFactory attributeManagerFactory, EventManagerFactory eventManagerFactory) {
        this.attributeManagerFactory =
                Objects.requireNonNull(attributeManagerFactory, "Attribute Manager Factory cannot be null.");
        this.eventManagerFactory = Objects.requireNonNull(eventManagerFactory, "Event Manager Factory cannot be null>");
        attributeManagers = new HashMap<>();
        eventManagers = new HashMap<>();
        items = new ArrayList<>();
        features = new ArrayList<>();
        areas = new ArrayList<>();
        events = new ArrayList<>();
        encounters = new ArrayList<>();
        actors = new ArrayList<>();
        entityKey = 0L;
        entityMap = new HashMap<>();
    }

    @Override
    public ItemBuilder createItemBuilder() {
        entityKey++;
        attributeManagers.put(entityKey, attributeManagerFactory.create());
        eventManagers.put(entityKey, eventManagerFactory.create());
        return ItemImpl.createBuilder(entityKey, this);
    }

    @Override
    public void addItem(Item item) {
        if (item != null) {
            items.add(item);
            entityMap.put(item.getName(), item);
        }
    }

    @Override
    public EntityService add(Entity entity, Attribute attribute) {
        attributeManagers.get(entity.getKey()).add(attribute);
        return this;
    }

    @Override
    public EntityService add(Entity entity, Attribute attribute, Integer value) {
        attributeManagers.get(entity.getKey()).add(attribute, value);
        return this;
    }

    @Override
    public void remove(Entity entity, Attribute attribute) {
        attributeManagers.get(entity.getKey()).remove(attribute);
    }

    @Override
    public void reset(Entity entity, Attribute attribute) {
        attributeManagers.get(entity.getKey()).reset(attribute);
    }

    @Override
    public boolean is(Entity entity, Attribute attribute) {
        return attributeManagers.get(entity.getKey()).is(attribute);
    }

    @Override
    public OptionalInt valueFor(Entity entity, Attribute attribute) {
        return attributeManagers.get(entity.getKey()).getValue(attribute);
    }

    @Override
    public Entity getEntityByName(String name) {
        return entityMap.get(name);
    }

    @Override
    public Item getItemByDescription(String description) {
        String[] descriptors = description.split(" ");
        for (Item item : items) {

        }
        return null;
    }

    @Override
    public Feature getFeatureByDescription(String description) {
        return null;
    }

    @Override
    public Area getAreaByName(String name) {
        return areas.stream().filter(a -> name.equals(a.getName())).findAny().get();
    }

    @Override
    public String getAttributeDescription(Entity key, Attribute attribute) {
        if (is(key, attribute)) {
            return attribute.getDoesApply();
        } else {
            return attribute.getDoesNotApply();
        }
    }

    @Override
    public void addAttributeDoesApply(Entity key, Attribute attribute, String text) {
        attributeManagers.get(key).addApply(attribute, text);
    }

    @Override
    public void addAttributeDoesNotApply(Entity key, Attribute attribute, String text) {
        attributeManagers.get(key).addDesNotApply(attribute, text);
    }

    @Override
    public String getAttributeApplicationText(Entity key, Attribute attribute) {
        return attributeManagers.get(key).getApplication(attribute, is(key, attribute));
    }

    @Override
    public EntityService add(Entity entity, EventType type, Event event) {
        eventManagers.get(entity.getKey()).add(type, event);
        return this;
    }

    @Override
    public void remove(Entity entity, EventType type, Event event) {
        eventManagers.get(entity.getKey()).remove(type, event);
    }

    @Override
    public List<Event> get(Entity entity, EventType type) {
        return eventManagers.get(entity.getKey()).getAllOfType(type);
    }

    @Override
    public List<Response> handle(Entity entity, EventType type, Player player, Adventure adventure) {
        LOGGER.info("Handling event type [{}]", type.name());
        List<Response> returnValue = new ArrayList<>();
        if (entity == null) {
            for (Long key : eventManagers.keySet()) {
                eventManagers.get(key).getAllOfType(type).stream().filter((e) -> e.pull(player, adventure))
                        .forEach((e) -> returnValue.addAll(e.apply(player, adventure)));
            }
        } else {
            eventManagers.get(entity.getKey()).getAllOfType(type).stream().filter((e) -> e.pull(player, adventure))
                    .forEach((e) -> returnValue.addAll(e.apply(player, adventure)));
        }
        return returnValue;
    }

    @Override
    public List<Actor> getActors() {
        return actors;
    }

    @Override
    public FeatureBuilder createFeatureBuilder() {
        entityKey++;
        attributeManagers.put(entityKey, attributeManagerFactory.create());
        eventManagers.put(entityKey, eventManagerFactory.create());
        return FeatureBasicImpl.createBuilder(entityKey, this);
    }

    @Override
    public void addFeature(Feature feature) {
        if (feature != null) {
            features.add(feature);
            entityMap.put(feature.getName(), feature);
        }
    }

    @Override
    public AreaBuilder createAreaBuilder() {
        entityKey++;
        attributeManagers.put(entityKey, attributeManagerFactory.create());
        eventManagers.put(entityKey, eventManagerFactory.create());
        return AreaImpl.createBuilder(entityKey, this);
    }

    @Override
    public void addArea(Area area) {
        if (area != null) {
            areas.add(area);
            entityMap.put(area.getName(), area);
        }
    }

    @Override
    public EventBuilder createEventBuilder() {
        entityKey++;
        attributeManagers.put(entityKey, attributeManagerFactory.create());
        eventManagers.put(entityKey, eventManagerFactory.create());
        return EventImpl.createBuilder(entityKey, this);
    }

    @Override
    public void addEvent(Event event) {
        if (event != null) {
            events.add(event);
            entityMap.put(event.getName(), event);
        }
    }

    @Override
    public EncounterBuilder createEncounterBuilder(Player player, Adventure adventure) {
        entityKey++;
        attributeManagers.put(entityKey, attributeManagerFactory.create());
        eventManagers.put(entityKey, eventManagerFactory.create());
        return EncounterImpl.createBuilder(entityKey, this, player, adventure);
    }

    @Override
    public void addEncounter(Encounter encounter) {
        if (encounter != null) {
            encounters.add(encounter);
            entityMap.put(encounter.getName(), encounter);
        }
    }

    @Override
    public ActorBuilder createActorBuilder() {
        entityKey++;
        attributeManagers.put(entityKey, attributeManagerFactory.create());
        eventManagers.put(entityKey, eventManagerFactory.create());
        return ActorImpl.createBuilder(entityKey, this);
    }

    @Override
    public void addActor(Actor actor) {
        if (actor != null) {
            actors.add(actor);
            entityMap.put(actor.getName(), actor);
        }
    }
}
