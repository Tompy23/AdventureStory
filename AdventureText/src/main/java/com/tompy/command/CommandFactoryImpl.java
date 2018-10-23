package com.tompy.command;

import com.tompy.adventure.AdventureUtils;
import com.tompy.entity.EntityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommandFactoryImpl implements CommandFactory {
    private static final Logger LOGGER = LogManager.getLogger(CommandFactoryImpl.class);
    private static final String COMMAND_CLOSE = "CLOSE";
    private static final String COMMAND_INVENTORY = "INVENTORY";
    private static final String COMMAND_MOVE = "MOVE";
    private static final String COMMAND_NULL = "NULL";
    private static final String COMMAND_OPEN = "OPEN";
    private static final String COMMAND_QUIT = "QUIT";
    private static final String COMMAND_RUN = "RUN";
    private static final String COMMAND_SEARCH = "SEARCH";
    private static final String COMMAND_TAKE = "TAKE";
    private static final String COMMAND_USE = "USE";
    private final EntityService entityService;

    private Map<String, CommandBuilderFactory> factoryMap = new HashMap<>();

    public CommandFactoryImpl(EntityService entityService) {
        factoryMap.put(COMMAND_CLOSE, CommandCloseImpl.createBuilderFactory());
        factoryMap.put(COMMAND_INVENTORY, CommandInventoryImpl.createBuilderFactory());
        factoryMap.put(COMMAND_MOVE, CommandMoveImpl.createBuilderFactory());
        factoryMap.put(COMMAND_NULL, CommandNullImpl.createBuilderFactory());
        factoryMap.put(COMMAND_OPEN, CommandOpenImpl.createBuilderFactory());
        factoryMap.put(COMMAND_QUIT, CommandQuitImpl.createBuilderFactory());
        factoryMap.put(COMMAND_RUN, CommandMoveImpl.createBuilderFactory());
        factoryMap.put(COMMAND_SEARCH, CommandSearchImpl.createBuilderFactory());
        factoryMap.put(COMMAND_TAKE, CommandTakeImpl.createBuilderFactory());
        factoryMap.put(COMMAND_USE, CommandUseImpl.createBuilderFactory());
        this.entityService = Objects.requireNonNull(entityService, "Entity Service cannot be null.");
    }

    @Override
    public Command createCommand(String[] inputs) {
        String[] commandInputs = new String[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            commandInputs[i] = inputs[i].toUpperCase();
        }

        if (factoryMap.containsKey(commandInputs[0])) {
            CommandBuilder cb = factoryMap.get(commandInputs[0]).createBuilder();
            if (null != cb) {
                if (cb != null) {
                    LOGGER.info("Creating Command [{}]", commandInputs[0]);
                    return cb.parts(commandInputs).entityService(entityService)
                        .type(AdventureUtils.getCommandType(commandInputs[0])).build();
                }
            }
        }

        LOGGER.info("Created Null Command");
        return factoryMap.get(COMMAND_NULL).createBuilder().entityService(entityService).build();
    }
}
