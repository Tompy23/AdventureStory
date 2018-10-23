package com.tompy.entity.Actor;

import com.tompy.adventure.Adventure;
import com.tompy.directive.MoveStrategyType;
import com.tompy.entity.EntityService;
import com.tompy.player.Player;

import java.util.Objects;

public class MoveStrategyFactoryImpl implements MoveStrategyFactory {
    protected final Player player;
    protected final Adventure adventure;
    protected final EntityService entityService;

    public MoveStrategyFactoryImpl(Player player, Adventure adventure, EntityService entityService) {
        this.player = Objects.requireNonNull(player, "Player cannot be null.");
        this.adventure = Objects.requireNonNull(adventure, "Adventure cannot be null.");
        this.entityService = Objects.requireNonNull(entityService, "Entity Service cannot be null.");
        adventure.setMoveStrategyFactory(this);
    }

    @Override
    public MoveStrategy createMoveStrategy(Actor actor, MoveStrategyType type) {
        switch (type) {
            case MOVE_RANDOM:
                return new MoveRandomImpl(actor, player, adventure, entityService);
        }
        return null;
    }
}
