package com.tompy.persistence;

import com.tompy.attribute.AttributeManager;
import com.tompy.entity.Actor.Actor;
import com.tompy.entity.Entity;
import com.tompy.entity.area.Area;
import com.tompy.entity.encounter.Encounter;
import com.tompy.entity.event.Event;
import com.tompy.entity.event.EventManager;
import com.tompy.entity.feature.Feature;
import com.tompy.entity.item.Item;
import com.tompy.player.Player;
import com.tompy.state.AdventureState;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class AdventureData implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public void saveAdventure() {

    }

    public void loadAdventure() {

    }
}
