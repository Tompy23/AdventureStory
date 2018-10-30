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

public class CommandNullImpl extends CommandBasicImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CommandNullImpl.class);

    private CommandNullImpl() {
        super(CommandType.COMMAND_NULL);
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandNullImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandNullImpl.CommandNullBuilderImpl();
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure, EntityService entityService) {
        LOGGER.info("Executing Command Null.");
        List<Response> returnValue = new ArrayList<>();
        returnValue.add(responseFactory.createBuilder().text("I do not understand").source("Unknown").build());
        return returnValue;
    }

    public static final class CommandNullBuilderImpl extends CommandBuilderImpl {

        @Override
        public CommandBuilder parts(String[] parts) {
            return this;
        }

        @Override
        public CommandBuilder type(CommandType type) {
            return this;
        }

        @Override
        public Command build() {
            return new CommandNullImpl();
        }
    }
}
