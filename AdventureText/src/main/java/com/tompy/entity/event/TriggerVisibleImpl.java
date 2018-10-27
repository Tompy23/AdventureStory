package com.tompy.entity.event;

import com.tompy.adventure.Adventure;
import com.tompy.entity.Entity;
import com.tompy.entity.EntityService;
import com.tompy.player.Player;

import static com.tompy.attribute.Attribute.VISIBLE;

public class TriggerVisibleImpl extends TriggerImpl {

    public TriggerVisibleImpl(Entity entity) {
        super(entity);
    }

    @Override
    public boolean pull(Player player, Adventure adventure, EntityService entityService) {
        return entityService.is(entity, VISIBLE);
    }
}
