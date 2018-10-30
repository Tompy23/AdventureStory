package com.tompy.entity;

import com.tompy.adventure.Adventure;
import com.tompy.attribute.Attribute;
import com.tompy.attribute.AttributeManager;
import com.tompy.attribute.AttributeManagerFactory;
import com.tompy.common.Builder;
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
import com.tompy.entity.event.*;
import com.tompy.entity.feature.Feature;
import com.tompy.entity.feature.FeatureBasicImpl;
import com.tompy.entity.feature.FeatureBuilder;
import com.tompy.entity.item.Item;
import com.tompy.entity.item.ItemBuilder;
import com.tompy.entity.item.ItemImpl;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.*;

public class EntityServiceImpl implements EntityService, Serializable {
    private static final long serialVersionUID = 1L;
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

    private EntityServiceImpl(AttributeManagerFactory attributeManagerFactory, EventManagerFactory eventManagerFactory,
            Map<Long, AttributeManager> attributeManagers, Map<Long, EventManager> eventManagers, List<Item> items,
            List<Feature> features, List<Area> areas, List<Event> events, List<Encounter> encounters,
            List<Actor> actors, Long entityKey, Map<String, Entity> entityMap) {
        this.attributeManagerFactory =
                Objects.requireNonNull(attributeManagerFactory, "Attribute Manager Factory cannot be null.");
        this.eventManagerFactory = Objects.requireNonNull(eventManagerFactory, "Event Manager Factory cannot be null>");
        this.attributeManagers = attributeManagers;
        this.eventManagers = eventManagers;
        this.items = items;
        this.features = features;
        this.areas = areas;
        this.events = events;
        this.encounters = encounters;
        this.actors = actors;
        this.entityKey = entityKey;
        this.entityMap = entityMap;
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
                eventManagers.get(key).getAllOfType(type).stream().filter((e) -> e.pull(player, adventure, this))
                        .forEach((e) -> returnValue.addAll(e.apply(player, adventure, this)));
            }
        } else {
            eventManagers.get(entity.getKey()).getAllOfType(type).stream()
                    .filter((e) -> e.pull(player, adventure, this))
                    .forEach((e) -> returnValue.addAll(e.apply(player, adventure, this)));
        }
        return returnValue;
    }

    @Override
    public List<Actor> getActors() {
        return Collections.unmodifiableList(actors);
    }

    @Override
    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public List<Feature> getFeatures() {
        return Collections.unmodifiableList(features);
    }

    @Override
    public List<Encounter> getEncounters() {
        return Collections.unmodifiableList(encounters);
    }

    @Override
    public List<Area> getAreas() {
        return Collections.unmodifiableList(areas);
    }

    @Override
    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }

    @Override
    public Map<String, Entity> getEntityMap() {
        return Collections.unmodifiableMap(entityMap);
    }

    @Override
    public Map<Long, EventManager> getEventManagers() {
        return Collections.unmodifiableMap(eventManagers);
    }

    @Override
    public Map<Long, AttributeManager> getAttributeManagers() {
        return Collections.unmodifiableMap(attributeManagers);
    }

    @Override
    public Long getEntityKey() {
        return entityKey;
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

    public static EntityServiceBuilder createBuilder(AttributeManagerFactory attributeManagerFactory,
            EventManagerFactory eventManagerFactory) {
        return new EntityServiceBuilder(attributeManagerFactory, eventManagerFactory);
    }


    public static final class EntityServiceBuilder implements Builder<EntityService> {
        private final AttributeManagerFactory attributeManagerFactory;
        private final EventManagerFactory eventManagerFactory;
        private Map<Long, AttributeManager> attributeManagers = null;
        private Map<Long, EventManager> eventManagers = null;
        private List<Item> items = null;
        private List<Feature> features = null;
        private List<Area> areas = null;
        private List<Event> events = null;
        private List<Encounter> encounters = null;
        private List<Actor> actors = null;
        private Long entityKey = null;
        private Map<String, Entity> entityMap = null;

        public EntityServiceBuilder(AttributeManagerFactory attributeManagerFactory,
                EventManagerFactory eventManagerFactory) {
            this.attributeManagerFactory =
                    Objects.requireNonNull(attributeManagerFactory, "Attribute Manager Factory cannot be null.");
            this.eventManagerFactory =
                    Objects.requireNonNull(eventManagerFactory, "Event Manager Factory cannot be null>");
        }

        @Override
        public EntityService build() {
            items = items == null ? new ArrayList<>() : new ArrayList<>(items);
            features = features == null ? new ArrayList<>() : new ArrayList<>(features);
            areas = areas == null ? new ArrayList<>() : new ArrayList<>(areas);
            events = events == null ? new ArrayList<>() : new ArrayList<>(events);
            encounters = encounters == null ? new ArrayList<>() : new ArrayList<>(encounters);
            actors = actors == null ? new ArrayList<>() : new ArrayList<>(actors);
            attributeManagers = attributeManagers == null ? new HashMap<>() : new HashMap<>(attributeManagers);
            eventManagers = eventManagers == null ? new HashMap<>() : new HashMap<>(eventManagers);
            entityMap = entityMap == null ? new HashMap<>() : new HashMap<>(entityMap);
            entityKey = entityKey == null ? 0L : entityKey;
            return new EntityServiceImpl(attributeManagerFactory, eventManagerFactory, attributeManagers, eventManagers,
                    items, features, areas, events, encounters, actors, entityKey, entityMap);
        }

        public EntityServiceBuilder attributeManagers(Map<Long, AttributeManager> attributeManagers) {
            this.attributeManagers = Collections.unmodifiableMap(attributeManagers);
            return this;
        }

        public EntityServiceBuilder eventManagers(Map<Long, EventManager> eventManagers) {
            this.eventManagers = Collections.unmodifiableMap(eventManagers);
            return this;
        }

        public EntityServiceBuilder items(List<Item> items) {
            this.items = Collections.unmodifiableList(items);
            return this;
        }

        public EntityServiceBuilder features(List<Feature> features) {
            this.features = Collections.unmodifiableList(features);
            return this;
        }

        public EntityServiceBuilder areas(List<Area> areas) {
            this.areas = Collections.unmodifiableList(areas);
            return this;
        }

        public EntityServiceBuilder events(List<Event> events) {
            this.events = Collections.unmodifiableList(events);
            return this;
        }

        public EntityServiceBuilder encounters(List<Encounter> encounters) {
            this.encounters = Collections.unmodifiableList(encounters);
            return this;
        }

        public EntityServiceBuilder actors(List<Actor> actors) {
            this.actors = Collections.unmodifiableList(actors);
            return this;
        }

        public EntityServiceBuilder entityKey(Long entityKey) {
            this.entityKey = entityKey;
            return this;
        }

        public EntityServiceBuilder entityMap(Map<String, Entity> entityMap) {
            this.entityMap = Collections.unmodifiableMap(entityMap);
            return this;
        }
    }
}
