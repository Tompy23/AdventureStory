package com.tompy.entity.item;

import com.tompy.adventure.Adventure;
import com.tompy.attribute.Attribute;
import com.tompy.directive.EventType;
import com.tompy.entity.Entity;
import com.tompy.entity.EntityService;
import com.tompy.entity.feature.Feature;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ItemWeaponImpl extends ItemImpl {
    private static final Logger LOGGER = LogManager.getLogger(ItemWeaponImpl.class);
    private final Feature target;

    public ItemWeaponImpl(Long key, String name, List<String> descriptors, String description, Feature target,
            int manipulationTicks) {
        super(key, name, descriptors, description, manipulationTicks);
        this.target = target;

    }

    @Override
    public List<Response> use(Player player, Adventure adventure, EntityService entityService) {
        LOGGER.info("Using weapon.");
        List<Response> returnValue = new ArrayList<>();

        adventure.setActionTicks(manipulationTicks);
        returnValue.addAll(entityService.handle(this, EventType.EVENT_WEAPON_BEFORE_ATTACK, player, adventure));

        if (entityService.valueFor(this, Attribute.VALUE).getAsInt() >
                entityService.valueFor(target, Attribute.VALUE).getAsInt()) {
            LOGGER.info("Weapon use success.");
            returnValue.addAll(entityService.handle(this, EventType.EVENT_WEAPON_ATTACK_SUCCESS, player, adventure));
        } else {
            LOGGER.info("Weapon use failure");
            returnValue.addAll(entityService.handle(this, EventType.EVENT_WEAPON_ATTACK_FAILURE, player, adventure));
        }

        returnValue.addAll(entityService.handle(this, EventType.EVENT_WEAPON_AFTER_ATTACK, player, adventure));

        return returnValue;
    }

    @Override
    public boolean hasTarget(Entity entity) {
        return entity.getKey().equals(target.getKey());
    }
}
