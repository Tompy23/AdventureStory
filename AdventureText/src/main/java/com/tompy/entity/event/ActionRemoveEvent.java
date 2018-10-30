package com.tompy.entity.event;

import com.tompy.adventure.Adventure;
import com.tompy.directive.EventType;
import com.tompy.entity.Entity;
import com.tompy.entity.EntityService;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActionRemoveEvent extends ActionImpl {
    public static final Logger LOGGER = LogManager.getLogger(ActionRemoveEvent.class);
    private final List<Event> events;
    private final EventType type;

    public ActionRemoveEvent(Entity entity, String[] responses, EventType type,
            List<Event> events) {
        super(entity, responses);
        this.events = events;
        this.type = type;
    }

    @Override
    public List<Response> apply(Player player, Adventure adventure, EntityService entityService) {
        LOGGER.info("Applying remove Event action.");
        List<Response> returnValue = new ArrayList<>();

        if (events != null) {
            for (Event event : events) {
                entityService.remove(entity, type, event);
                LOGGER.info("Removing Event [{}]", event.getName());
            }
            returnValue.addAll(responses.stream().
                    map((r) -> responseFactory.createBuilder().source(source).text(substitution(r, entityService))
                            .build()).collect(Collectors.toList()));
        }

        return returnValue;
    }
}
