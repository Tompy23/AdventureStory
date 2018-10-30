package com.tompy.entity.Actor;

import com.tompy.adventure.Adventure;
import com.tompy.adventure.AdventureUtils;
import com.tompy.directive.Direction;
import com.tompy.entity.EntityService;
import com.tompy.entity.area.Area;
import com.tompy.exit.Exit;
import com.tompy.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MoveRandomImpl extends MoveStrategyBaseImpl implements MoveStrategy {
    private static final Logger LOGGER = LogManager.getLogger(MoveRandomImpl.class);

    public MoveRandomImpl(Actor actor, Player player, Adventure adventure) {
        super(actor, player, adventure);
    }

    @Override
    public Direction getMove(EntityService entityService) {
        Area startArea = entityService.getAreaByName(actor.getArea().getName());
        LOGGER.info(startArea.getName());
        Direction go = AdventureUtils.getRandomDirection();
        LOGGER.info(go.getDescription());
        Exit possibleExit = startArea.getExitForDirection(go);
        return possibleExit != null ? go : null;
    }
}
