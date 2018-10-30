package com.tompy.entity.feature;

import com.tompy.adventure.Adventure;
import com.tompy.attribute.Attribute;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.EntityService;
import com.tompy.exit.Exit;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.tompy.attribute.Attribute.LOCKED;
import static com.tompy.attribute.Attribute.OPEN;
import static com.tompy.attribute.Attribute.VISIBLE;
import static com.tompy.directive.EventType.EVENT_FEATURE_CLOSE;
import static com.tompy.directive.EventType.EVENT_FEATURE_OPEN;
import static com.tompy.directive.EventType.EVENT_FEATURE_OPEN_BUT_LOCKED;

/**
 * A Door is something that can be hidden, open/closed, locked, etc.
 * A door interacts with an Exit, the door is just a feature which controls the exit.  The Exit itself
 * controls whether or not the player can pass through.  The door and exit should be in sync.
 * A Door can be trapped.
 */
public class FeatureDoorImpl extends FeatureBasicImpl {
    private static final Logger LOGGER = LogManager.getLogger(FeatureDoorImpl.class);
    private Exit exit;

    protected FeatureDoorImpl(Long key, String name, List<String> descriptors, String description,
            EntityService entityService, Exit exit, int manipulationTicks) {
        super(key, name, descriptors, description,  manipulationTicks);
        this.exit = exit;
        entityService.add(this, VISIBLE);
    }

    @Override
    public List<Response> open(Player player, Adventure adventure, EntityService entityService) {
        LOGGER.info("Opening [{}, {}]", this.getName(), exit.toString());
        List<Response> returnValue = new ArrayList<>();

        if (entityService.is(this, VISIBLE)) {
            if (!entityService.is(this, OPEN) && !entityService.is(this, LOCKED)) {
                entityService.add(this, OPEN);
                exit.open();
                adventure.setActionTicks(manipulationTicks);
                returnValue.addAll(entityService.handle(this, EVENT_FEATURE_OPEN, player, adventure));
            } else if (entityService.is(this, LOCKED)) {
                returnValue
                        .addAll(entityService.handle(this, EVENT_FEATURE_OPEN_BUT_LOCKED, player, adventure));
            }
        }

        return returnValue;
    }

    @Override
    public List<Response> close(Player player, Adventure adventure, EntityService entityService) {
        LOGGER.info("Closing [{}, {}]", this.getName(), exit.toString());
        List<Response> returnValue = new ArrayList<>();

        if (entityService.is(this, VISIBLE)) {
            if (entityService.is(this, OPEN)) {
                entityService.remove(this, OPEN);
                exit.close();
                adventure.setActionTicks(manipulationTicks);
                returnValue.addAll(entityService.handle(this, EVENT_FEATURE_CLOSE, player, adventure));
            }
        }

        return returnValue;
    }
}
