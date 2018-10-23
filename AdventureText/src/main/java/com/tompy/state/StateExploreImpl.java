package com.tompy.state;

import com.tompy.adventure.Adventure;
import com.tompy.command.Command;
import com.tompy.entity.EntityService;
import com.tompy.io.UserInput;
import com.tompy.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;

import static com.tompy.directive.EventType.EVENT_EXPLORING;

public class StateExploreImpl extends AdventureStateBaseImpl implements AdventureState {
    private final static Logger LOGGER = LogManager.getLogger(StateExploreImpl.class);

    public StateExploreImpl(Player player, Adventure adventure, UserInput userInput, PrintStream outStream,
            EntityService entityService) {
        super(player, adventure, userInput, outStream, entityService);

    }

    @Override
    public void start() {
        LOGGER.info("Start Exploring");
    }

    @Override
    public void process() {
        Command command = userInput.getCommand();
        if (null != command) {
            command.execute(player, adventure).stream().forEachOrdered((r) -> outStream.println(r.render()));
            entityService.getActors().stream().forEachOrdered((a) -> a.takeAction(player, adventure).stream()
                    .forEachOrdered((r) -> outStream.println(r.render())));
            entityService.handle(null, EVENT_EXPLORING, player, adventure).stream()
                    .forEachOrdered((a) -> outStream.println(a.render()));
        }

        outStream.println();
    }

    @Override
    public void end() {
        LOGGER.info("End Exploring");
    }
}
