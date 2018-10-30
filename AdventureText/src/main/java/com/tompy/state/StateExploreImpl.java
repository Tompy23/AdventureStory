package com.tompy.state;

import com.tompy.adventure.Adventure;
import com.tompy.command.Command;
import com.tompy.entity.EntityService;
import com.tompy.io.UserIO;
import com.tompy.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;

import static com.tompy.directive.EventType.EVENT_EXPLORING;

public class StateExploreImpl extends AdventureStateBaseImpl implements AdventureState {
    private final static Logger LOGGER = LogManager.getLogger(StateExploreImpl.class);

    public StateExploreImpl(Player player, Adventure adventure) {
        super(player, adventure);

    }

    @Override
    public void start(EntityService entityService, UserIO io) {
        LOGGER.info("Start Exploring");
    }

    @Override
    public void process(EntityService entityService, UserIO io) {
        Command command = io.getCommand();
        if (null != command) {
            command.execute(player, adventure, entityService).stream().forEachOrdered((r) -> io.println(r.render()));
            entityService.getActors().stream().forEachOrdered((a) -> a.takeAction(player, adventure, entityService).stream()
                    .forEachOrdered((r) -> io.println(r.render())));
            entityService.handle(null, EVENT_EXPLORING, player, adventure).stream()
                    .forEachOrdered((a) -> io.println(a.render()));
        }

        io.println();
    }

    @Override
    public void end(EntityService entityService, UserIO io) {
        LOGGER.info("End Exploring");
    }
}
