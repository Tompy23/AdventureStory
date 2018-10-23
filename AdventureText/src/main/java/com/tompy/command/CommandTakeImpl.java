package com.tompy.command;

import com.tompy.adventure.Adventure;
import com.tompy.adventure.AdventureUtils;
import com.tompy.directive.CommandType;
import com.tompy.entity.EntityService;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.item.Item;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.tompy.attribute.Attribute.VISIBLE;
import static com.tompy.directive.CommandType.COMMAND_TAKE;
import static com.tompy.directive.CommandType.COMMAND_TAKE_FROM;

public class CommandTakeImpl extends CommandBasicImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CommandTakeImpl.class);
    protected final String target;

    protected CommandTakeImpl(CommandType type, EntityService entityService, String target) {
        super(type != null ? type : COMMAND_TAKE, entityService);
        this.target = Objects.requireNonNull(target, "Target cannot be null.");
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandTakeImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandTakeImpl.CommandTakeBuilderImpl();
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure) {
        LOGGER.info("Executing Command Take");
        List<Response> returnValue = new ArrayList<>();

        Optional<Item> optObject = EntityUtil
                .findVisibleItemByDescription(entityService, player.getArea().getAllItems(), target, adventure.getUI());

        if (optObject.isPresent()) {
            Item object = optObject.get();
            if (entityService.is(object, VISIBLE)) {
                if (player.addItem(object)) {
                    player.getArea().removeItem(object);
                    returnValue.add(responseFactory.createBuilder().source("CommandTake")
                            .text(String.format("%s now has %s", player.getName(), object.getDescription())).build());
                } else {
                    // TODO Inventory full?  Or some other issue?
                    returnValue
                            .add(responseFactory.createBuilder().source("CommandTake").text("Inventory full.").build());
                }
            }
        } else {
            returnValue.add(responseFactory.createBuilder().source("CommandTake")
                    .text(String.format("Can't see %s", target.toLowerCase())).build());
        }

        return returnValue;
    }

    public static final class CommandTakeBuilderImpl extends CommandBuilderImpl {
        private String item;
        private String target;

        @Override
        public Command build() {
            switch (type) {
                case COMMAND_TAKE_FROM:
                    return new CommandTakeFromImpl(type, entityService, item, target);
                default:
                    return new CommandTakeImpl(type, entityService, target);
            }
        }

        @Override
        public CommandBuilder parts(String[] parts) {
            String[] commands = AdventureUtils.parseCommand(parts, Arrays.asList(new String[]{"from"}));
            if (commands.length == 1) {
                target = commands[0];
                type = COMMAND_TAKE;
            } else {
                item = commands[0];
                target = commands[1];
                type = COMMAND_TAKE_FROM;
            }

            return this;
        }

        @Override
        public CommandBuilder type(CommandType type) {
            // type is defined when parsing parts
            return this;
        }
    }
}
