package com.tompy.entity.Actor;

import com.tompy.adventure.Adventure;
import com.tompy.directive.Direction;
import com.tompy.entity.EntityService;
import com.tompy.player.Player;

public class MoveFollowImpl extends MoveStrategyBaseImpl implements MoveStrategy {

    public MoveFollowImpl(Actor actor, Player player, Adventure adventure) {
        super(actor, player, adventure);
    }

    @Override
    public Direction getMove(EntityService entityService) {
        for (Direction direction : Direction.values()) {
            if (player.getArea()
                    .equals(actor.getArea().getExitForDirection(direction).getConnectedArea(actor.getArea()))) {
                return direction;
            }
        }
        return null;
    }
}
