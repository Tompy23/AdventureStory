package com.tompy.command;

import com.tompy.adventure.Adventure;
import com.tompy.directive.CommandType;
import com.tompy.entity.EntityService;
import com.tompy.player.Player;
import com.tompy.response.Response;

import java.util.List;

public class CommandMapImpl extends CommandBasicImpl implements Command{

    private CommandMapImpl() {
        super(CommandType.COMMAND_MAP);
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandMapImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandMapImpl.CommandMapBuilderImpl();
    }


    @Override
    public List<Response> execute(Player player, Adventure adventure, EntityService entityService) {
        return player.getArea().showMap(player, adventure, entityService);
    }

    public static final class CommandMapBuilderImpl extends CommandBuilderImpl {

        @Override
        public CommandBuilder parts(String[] parts) {
            return this;
        }

        @Override
        public Command build() {
            return new CommandMapImpl();
        }
    }
}
