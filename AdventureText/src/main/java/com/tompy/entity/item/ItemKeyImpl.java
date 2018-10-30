package com.tompy.entity.item;

import com.tompy.adventure.Adventure;
import com.tompy.attribute.Attribute;
import com.tompy.entity.Entity;
import com.tompy.entity.EntityService;
import com.tompy.entity.feature.Feature;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.tompy.directive.EventType.*;

/**
 * A key is used to lock/unlock a feature.
 */
public class ItemKeyImpl extends ItemImpl {
    private static final Logger LOGGER = LogManager.getLogger(ItemKeyImpl.class);
    private final Feature target;

    public ItemKeyImpl(Long key, String name, List<String> descriptors, String description, EntityService entityService,
        Feature target, int manipulationTicks) {
        super(key, name, descriptors, description, entityService, manipulationTicks);
        this.target = target;
    }

    @Override
    public List<Response> use(Player player, Adventure adventure, EntityService entityService) {
        List<Response> returnValue = new ArrayList<>();

        LOGGER.info("Using key [{}] on [{}]", getName(), target.getName());

        adventure.setActionTicks(manipulationTicks);
        returnValue.addAll(entityService.handle(this, EVENT_KEY_BEFORE_USE, player, adventure));
        returnValue.addAll(entityService.handle(target, EVENT_KEY_BEFORE_USE, player, adventure));

        if (entityService.is(target, Attribute.LOCKED)) {
            returnValue.addAll(target.unlock(player, adventure, entityService));
            returnValue.addAll(entityService.handle(this, EVENT_KEY_LOCK, player, adventure));
            returnValue.addAll(entityService.handle(target, EVENT_KEY_LOCK, player, adventure));
        } else {
            returnValue.addAll(target.lock(player, adventure, entityService));
            returnValue.addAll(entityService.handle(this, EVENT_KEY_UNLOCK, player, adventure));
            returnValue.addAll(entityService.handle(target, EVENT_KEY_UNLOCK, player, adventure));
        }
        returnValue.addAll(entityService.handle(this, EVENT_KEY_AFTER_USE, player, adventure));
        returnValue.addAll(entityService.handle(target, EVENT_KEY_AFTER_USE, player, adventure));

        return returnValue;
    }

    @Override
    public List<Response> misUse(Feature feature, Player player, Adventure adventure, EntityService entityService) {
        List<Response> returnValue = new ArrayList<>();
        returnValue.addAll(entityService.handle(this, EVENT_ITEM_MISUSE, player, adventure));
        returnValue.addAll(entityService.handle(feature, EVENT_FEATURE_MISUSE, player, adventure));
        return returnValue;
    }

    @Override
    public boolean hasTarget(Entity entity) {
        return entity.getKey().equals(target.getKey());
    }

}
