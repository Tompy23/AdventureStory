package com.tompy.entity.item;

import com.tompy.adventure.Adventure;
import com.tompy.entity.EntityService;
import com.tompy.entity.event.Event;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.tompy.directive.EventType.EVENT_AFTER_POTION;
import static com.tompy.directive.EventType.EVENT_BEFORE_POTION;

public class ItemPotionImpl extends ItemImpl {
    private static final Logger LOGGER = LogManager.getLogger(ItemPotionImpl.class);
    private final Event event;

    public ItemPotionImpl(Long key, String name, List<String> descriptors, String description,
            EntityService entityService, Event event, int manipulationTicks) {
        super(key, name, descriptors, description, entityService, manipulationTicks);
        this.event = event;
    }

    @Override
    public List<Response> use(Player player, Adventure adventure, EntityService entityService) {
        LOGGER.info("Drinking potion.");
        List<Response> returnValue = new ArrayList<>();

        adventure.setActionTicks(manipulationTicks);
        returnValue.addAll(entityService.handle(this, EVENT_BEFORE_POTION, player, adventure));

        if (event.pull(player, adventure, entityService)) {
            returnValue.addAll(event.apply(player, adventure, entityService));
        }

        returnValue.addAll(entityService.handle(this, EVENT_AFTER_POTION, player, adventure));

        return returnValue;
    }
}
