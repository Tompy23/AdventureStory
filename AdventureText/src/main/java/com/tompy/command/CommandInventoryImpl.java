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

public class CommandInventoryImpl extends CommandBasicImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CommandInventoryImpl.class);

    private CommandInventoryImpl(CommandType type) {
        super(type);
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandInventoryImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandInventoryBuilderImpl();
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure, EntityService entityService) {
        LOGGER.info("Executing Command Inventory");
        List<Response> returnValue = new ArrayList<>();

        if (!player.getInventory().isEmpty()) {
            player.getInventory().stream().forEach((i) -> returnValue
                    .add(responseFactory.createBuilder().source(player.getName()).text(i.getDescription()).build()));
        } else {
            returnValue.add(responseFactory.createBuilder().source(player.getName()).text("Inventory Empty").build());
        }
        returnValue.add(responseFactory.createBuilder().source(player.getName())
                .text(String.format("$%d", player.moneyValue())).build());

        return returnValue;
    }

    public static final class CommandInventoryBuilderImpl extends CommandBuilderImpl {

        @Override
        public CommandBuilder parts(String[] parts) {
            return this;
        }

        @Override
        public Command build() {
            return new CommandInventoryImpl(type);
        }
    }
}
