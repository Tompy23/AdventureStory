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

public class CommandSearchInImpl extends CommandSearchImpl {
    private static final Logger LOGGER = LogManager.getLogger(CommandSearchInImpl.class);

    public CommandSearchInImpl(CommandType type, String target, String secondaryTarget) {
        super(type, target, secondaryTarget);
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure, EntityService entityService) {
        LOGGER.info("Executing Search In");
        List<Response> returnValue = new ArrayList<>();

        returnValue
            .add(responseFactory.createBuilder().text("not implemented").source(this.type.getDescription()).build());

        return returnValue;
    }
}