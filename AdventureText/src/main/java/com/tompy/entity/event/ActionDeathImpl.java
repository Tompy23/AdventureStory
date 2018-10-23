package com.tompy.entity.event;

import com.tompy.adventure.Adventure;
import com.tompy.entity.Entity;
import com.tompy.entity.EntityService;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActionDeathImpl extends ActionImpl {
    public static final Logger LOGGER = LogManager.getLogger(ActionDeathImpl.class);

    public ActionDeathImpl(Entity entity, EntityService entityService, String[] responses) {
        super(entity, entityService, responses);
    }

    @Override public List<Response> apply(Player player, Adventure adventure) {
        LOGGER.info("Stopping the adventure.");
        adventure.stop();
        List<Response> returnValue = new ArrayList<>();
        returnValue.addAll(responses.stream().
                map((r) -> responseFactory.createBuilder().source(source).text(substitution(r)).build())
                .collect(Collectors.toList()));
        return returnValue;

    }
}
