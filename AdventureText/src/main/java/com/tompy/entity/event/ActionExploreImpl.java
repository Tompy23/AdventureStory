package com.tompy.entity.event;

import com.tompy.adventure.Adventure;
import com.tompy.entity.Entity;
import com.tompy.entity.EntityService;
import com.tompy.player.Player;
import com.tompy.response.Response;
import com.tompy.state.AdventureStateFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ActionExploreImpl extends ActionImpl {
    public static final Logger LOGGER = LogManager.getLogger(ActionExploreImpl.class);
    private final AdventureStateFactory stateFactory;

    public ActionExploreImpl(Entity entity, String[] respones,
            AdventureStateFactory stateFactory) {
        super(entity, respones);
        this.stateFactory = Objects.requireNonNull(stateFactory, "State Factory cannot be null.");
    }

    @Override
    public List<Response> apply(Player player, Adventure adventure, EntityService entityService) {
        LOGGER.info("Start Exploring State.");
        adventure.changeState(stateFactory.getExploreState(), entityService);
        List<Response> returnValue = new ArrayList<>();
        returnValue.addAll(responses.stream().
                map((r) -> responseFactory.createBuilder().source(source).text(substitution(r, entityService)).build())
                .collect(Collectors.toList()));
        return returnValue;
    }
}
