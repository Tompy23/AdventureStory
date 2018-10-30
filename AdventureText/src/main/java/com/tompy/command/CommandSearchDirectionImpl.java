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

public class CommandSearchDirectionImpl extends CommandSearchImpl {
    private static final Logger LOGGER = LogManager.getLogger(CommandSearchDirectionImpl.class);

    public CommandSearchDirectionImpl(CommandType type, String target, String secondaryTarget) {
        super(type, target, secondaryTarget);
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure, EntityService entityService) {
        LOGGER.info("Executing Search Direction");
        List<Response> returnValue = new ArrayList<>();

        returnValue.addAll(player.getArea()
                .searchDirection(AdventureUtils.getDirection(target), player, adventure, entityService));

        return returnValue;
    }
}