package com.tompy.persistence;

import com.tompy.attribute.AttributeManager;
import com.tompy.common.Builder;
import com.tompy.entity.Actor.Actor;
import com.tompy.entity.Entity;
import com.tompy.entity.area.Area;
import com.tompy.entity.encounter.Encounter;
import com.tompy.entity.event.Event;
import com.tompy.entity.event.EventManager;
import com.tompy.entity.feature.Feature;
import com.tompy.entity.item.Item;
import com.tompy.map.AdventureMap;
import com.tompy.player.Player;
import com.tompy.state.AdventureState;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AdventureData implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Map<Long, AttributeManager> attributeManagers;
    private final Map<Long, EventManager> eventManagers;
    private final List<Item> items;
    private final List<Feature> features;
    private final List<Area> areas;
    private final List<Event> events;
    private final List<Encounter> encounters;
    private final List<Actor> actors;
    private final Long entityKey;
    private final Map<String, Entity> entityMap;
    private final AdventureState currentState;
    private final int currentTick;
    private final int actionTicks;
    private final Player player;
    private final Map<String, AdventureMap> maps;

    private AdventureData(Map<Long, AttributeManager> attributeManagers, Map<Long, EventManager> eventManagers,
            List<Item> items, List<Feature> features, List<Area> areas, List<Event> events, List<Encounter> encounters,
            List<Actor> actors, Long entityKey, Map<String, Entity> entityMap, AdventureState currentState,
            int currentTick, int actionTicks, Player player, Map<String, AdventureMap> maps) {
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
        this.currentState = currentState;
        this.currentTick = currentTick;
        this.actionTicks = actionTicks;
        this.player = player;
        this.maps = maps;
    }

    public Map<Long, AttributeManager> getAttributeManagers() {
        return attributeManagers;
    }

    public Map<Long, EventManager> getEventManagers() {
        return eventManagers;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Encounter> getEncounters() {
        return encounters;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public Long getEntityKey() {
        return entityKey;
    }

    public Map<String, Entity> getEntityMap() {
        return entityMap;
    }

    public AdventureState getCurrentState() {
        return currentState;
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public int getActionTicks() {
        return actionTicks;
    }

    public Player getPlayer() {
        return player;
    }

    public Map<String, AdventureMap> getMaps() {
        return maps;
    }

    public static final class AdventureDataBuilder implements Builder<AdventureData> {
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
        private AdventureState currentState;
        private int currentTick;
        private int actionTicks;
        private Player player;
        private Map<String, AdventureMap> maps;

        @Override
        public AdventureData build() {
            return new AdventureData(attributeManagers, eventManagers, items, features, areas, events, encounters,
                    actors, entityKey, entityMap, currentState, currentTick, actionTicks, player, maps);
        }
        
        public AdventureDataBuilder attributeManagers(Map<Long, AttributeManager> attributeManagers) {
            this.attributeManagers = Collections.unmodifiableMap(attributeManagers);
            return this;
        }

        public AdventureDataBuilder eventManagers(Map<Long, EventManager> eventManagers) {
            this.eventManagers = Collections.unmodifiableMap(eventManagers);
            return this;            
        }

        public AdventureDataBuilder items(List<Item> items) {
            this.items = Collections.unmodifiableList(items);
            return this;
        }

        public AdventureDataBuilder features(List<Feature> features) {
            this.features = Collections.unmodifiableList(features);
            return this;
        }

        public AdventureDataBuilder areas(List<Area> areas) {
            this.areas = Collections.unmodifiableList(areas);
            return this;
        }

        public AdventureDataBuilder events(List<Event> events) {
            this.events = Collections.unmodifiableList(events);
            return this;
        }

        public AdventureDataBuilder encounters(List<Encounter> encounters) {
            this.encounters = Collections.unmodifiableList(encounters);
            return this;
        }

        public AdventureDataBuilder actors(List<Actor> actors) {
            this.actors = Collections.unmodifiableList(actors);
            return this;
        }

        public AdventureDataBuilder entityKey(Long entityKey) {
            this.entityKey = entityKey;
            return this;
        }

        public AdventureDataBuilder entityMap(Map<String, Entity> entityMap) {
            this.entityMap = Collections.unmodifiableMap(entityMap);
            return this;
        }

        public AdventureDataBuilder currentState(AdventureState currentState) {
            this.currentState = currentState;
            return this;
        }

        public AdventureDataBuilder currentTick(int currentTick) {
            this.currentTick = currentTick;
            return this;
        }

        public AdventureDataBuilder actionTicks(int actionTicks) {
            this.actionTicks = actionTicks;
            return this;
        }

        public AdventureDataBuilder player(Player player) {
            this.player = player;
            return this;
        }

        public AdventureDataBuilder maps(Map<String, AdventureMap> maps) {
            this.maps = maps;
            return this;
        }
    }
}
