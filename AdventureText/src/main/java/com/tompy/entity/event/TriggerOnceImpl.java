package com.tompy.entity.event;

import com.tompy.adventure.Adventure;
import com.tompy.entity.Entity;
import com.tompy.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TriggerOnceImpl extends TriggerImpl {
    public static final Logger LOGGER = LogManager.getLogger(TriggerOnceImpl.class);

    public TriggerOnceImpl(Entity entity) {
        super(entity);
        trigger = true;
    }

    @Override
    public boolean pull(Player player, Adventure adventure) {
        LOGGER.info("Checking trigger.");
        boolean returnValue = trigger;
        trigger = false;
        return returnValue;
    }
}
