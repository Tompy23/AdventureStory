package com.tompy.command;

import com.tompy.adventure.Adventure;
import com.tompy.directive.CommandType;
import com.tompy.entity.EntityService;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CommandQuitImpl extends CommandBasicImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CommandQuitImpl.class);

    private CommandQuitImpl() {
        super(CommandType.COMMAND_QUIT);
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandQuitImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandQuitImpl.CommandQuitBuilderImpl();
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure, EntityService entityService) {
        LOGGER.info("Executing Command Quit");
        List<Response> returnValue = new ArrayList<>();

        returnValue.add(responseFactory.createBuilder().source(type.getDescription()).text("Goodbye").build());

        adventure.stop();

        return returnValue;
    }

    public static final class CommandQuitBuilderImpl extends CommandBuilderImpl {
        @Override
        public Command build() {
            return new CommandQuitImpl();
        }

        @Override
        public CommandBuilder parts(String[] parts) {
            return this;
        }

        @Override
        public CommandBuilder type(CommandType type) {
            return this;
        }
    }
}
