package com.tompy.entity.Actor;

import com.tompy.adventure.Adventure;
import com.tompy.entity.EntityService;
import com.tompy.player.Player;

import java.util.Objects;

public abstract class MoveStrategyBaseImpl implements MoveStrategy {
    protected Actor actor;
    protected Player player;
    protected Adventure adventure;

    public MoveStrategyBaseImpl(Actor actor, Player player, Adventure adventure) {
        this.actor = Objects.requireNonNull(actor, "Actor cannot be null.");
        this.player = Objects.requireNonNull(player, "Player cannot be null.");
        this.adventure = Objects.requireNonNull(adventure, "Adventure cannot be null.");
    }


}
