package com.tompy.command;

import com.tompy.adventure.Adventure;
import com.tompy.adventure.AdventureUtils;
import com.tompy.directive.CommandType;
import com.tompy.directive.Direction;
import com.tompy.entity.EntityService;
import com.tompy.entity.area.Area;
import com.tompy.exit.Exit;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CommandMoveImpl extends CommandBasicImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CommandMoveImpl.class);
    protected Direction direction = Direction.DIRECTION_NORTH;

    private CommandMoveImpl(CommandType type, String dir, EntityService entityService) {
        super(type != null ? type : CommandType.COMMAND_MOVE, entityService);
        if (AdventureUtils.isDirection(dir)) {
            direction = AdventureUtils.getDirection(dir);
        } else {
            direction = null;
        }
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandMoveImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandMoveBuilderImpl();
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure) {
        LOGGER.info("Executing Command Move.  direction: {}", direction != null ? direction.getDescription() : "???");
        List<Response> returnValue = new ArrayList<>();
        Area currentArea = player.getArea();
        Exit targetExit = currentArea.getExitForDirection(direction);

        if (direction == null) {
            returnValue.add(responseFactory.createBuilder().source("MOVE").text("Unknown direction").build());
        }

        if (null != targetExit) {
            if (targetExit.isOpen()) {
                Area targetArea = targetExit.getConnectedArea(currentArea);
                returnValue.addAll(currentArea.exit(direction, player, adventure, entityService));
                adventure.setActionTicks(targetExit.getPassThruTicks());
                returnValue.addAll(targetExit.passThru(direction));
                player.setArea(targetArea);
                returnValue.addAll(targetArea
                        .enter(AdventureUtils.getOppositeDirection(direction), player, adventure, entityService));
            }
        }

        return returnValue;
    }

    public static final class CommandMoveBuilderImpl extends CommandBuilderImpl {
        private String dir;

        @Override
        public CommandBuilder parts(String[] parts) {
            this.dir = parts[1];
            return this;
        }

        public Command build() {
            return new CommandMoveImpl(type, dir, entityService);
        }
    }
}
