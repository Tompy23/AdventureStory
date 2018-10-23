package com.tompy.entity.Actor;

import com.tompy.adventure.Adventure;
import com.tompy.entity.EntityService;
import com.tompy.player.Player;

import java.util.Objects;

public abstract class MoveStrategyBaseImpl implements MoveStrategy {
    protected Actor actor;
    protected Player player;
    protected Adventure adventure;
    protected EntityService entityService;

    public MoveStrategyBaseImpl(Actor actor, Player player, Adventure adventure, EntityService entityService) {
        this.actor = Objects.requireNonNull(actor, "Actor cannot be null.");
        this.player = Objects.requireNonNull(player, "Player cannot be null.");
        this.adventure = Objects.requireNonNull(adventure, "Adventure cannot be null.");
        this.entityService = Objects.requireNonNull(entityService, "Entity Service cannot be null.");
    }


}
