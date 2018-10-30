package com.tompy.command;

import com.tompy.adventure.Adventure;
import com.tompy.adventure.AdventureUtils;
import com.tompy.directive.CommandType;
import com.tompy.entity.EntityService;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.tompy.directive.CommandType.*;

public class CommandSearchImpl extends CommandBasicImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CommandSearchImpl.class);
    protected String target;
    protected String secondaryTarget;

    protected CommandSearchImpl(CommandType type, String target, String secondaryTarget) {
        super(type);
        this.target = target;
        this.secondaryTarget = secondaryTarget;
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandSearchImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandSearchImpl.CommandSearchBuilderImpl();
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure, EntityService entityService) {
        LOGGER.info("Executing Search");
        List<Response> returnValue = new ArrayList<>();

        returnValue.addAll(player.getArea().search(player, adventure, entityService));

        return returnValue;
    }

    public static final class CommandSearchBuilderImpl extends CommandBuilderImpl {
        private String target = null;
        private String secondaryTarget = null;

        @Override
        public Command build() {
            switch (type) {
                case COMMAND_SEARCH_DIRECTION:
                    return new CommandSearchDirectionImpl(type, target, secondaryTarget);
                case COMMAND_SEARCH_IN:
                    return new CommandSearchInImpl(type, target, secondaryTarget);
                case COMMAND_SEARCH_ON:
                    return new CommandSearchOnImpl(type, target, secondaryTarget);
                case COMMAND_SEARCH_FEATURE:
                    return new CommandSearchFeatureImpl(type, target, secondaryTarget);
                default:
                    return new CommandSearchImpl(type, target, secondaryTarget);
            }
        }

        @Override
        public CommandBuilder parts(String[] parts) {
            if (parts.length > 1) {
                if (AdventureUtils.isDirection(parts[1])) {
                    target = parts[1];
                    type = COMMAND_SEARCH_DIRECTION;
                } else if (parts.length > 3 && (parts[2].equals("ON"))) {
                    target = parts[1];
                    secondaryTarget = parts[3];
                    type = COMMAND_SEARCH_ON;
                } else if (parts.length > 2 && parts[1].equals("IN")) {
                    target = parts[2];
                    type = COMMAND_SEARCH_IN;
                } else if (parts.length > 2 && parts[1].equals("ON")) {
                    target = parts[2];
                    type = COMMAND_SEARCH_ON;
                } else {
                    StringBuilder targetSb = new StringBuilder();
                    for (int i = 1; i < parts.length; i++) {
                        targetSb.append(parts[i] + " ");
                    }
                    target = targetSb.toString().trim();
                    type = COMMAND_SEARCH_FEATURE;
                }
            } else {
                type = CommandType.COMMAND_SEARCH;
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
