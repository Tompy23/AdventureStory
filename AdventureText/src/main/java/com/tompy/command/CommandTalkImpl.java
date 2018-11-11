package com.tompy.command;

import com.tompy.adventure.Adventure;
import com.tompy.adventure.AdventureUtils;
import com.tompy.directive.CommandType;
import com.tompy.entity.Actor.Actor;
import com.tompy.entity.EntityService;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.feature.Feature;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.tompy.directive.CommandType.COMMAND_TALK;

public class CommandTalkImpl extends CommandBasicImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CommandTalkImpl.class);

    private final String target;

    protected CommandTalkImpl(CommandType type, String target) {
        super(type);
        this.target = Objects.requireNonNull(target, "Target cannot be null.");
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandTalkImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandTalkImpl.CommandTalkBuilderImpl();
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure, EntityService entityService) {
        //TODO Starts an encounter with the target
        // This is done by firing an "Encounter" type action event
        // The event will then change the state appropriately and handle any other setup
        LOGGER.info("Executing Command Open");
        List<Response> returnValue = new ArrayList<>();

        Optional<Actor> optObject = EntityUtil
                .findVisibleActorByDescription(entityService, player.getArea().getAllActors(), target,
                        adventure.getUI());

        return null;
    }

    public static final class CommandTalkBuilderImpl extends CommandBuilderImpl {
        private String target;

        @Override
        public CommandBuilder parts(String[] parts) {
            target = "";
            String[] commands = AdventureUtils.parseCommand(parts, Arrays.asList(new String[]{"to", "with"}));
            if (commands.length == 1) {
                target = commands[0];
            }
            return this;
        }

        @Override
        public Command build() {
            return new CommandTalkImpl(COMMAND_TALK, target);
        }
    }
}