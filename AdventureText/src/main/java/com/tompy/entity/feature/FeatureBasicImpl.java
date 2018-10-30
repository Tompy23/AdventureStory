package com.tompy.entity.feature;

import com.tompy.adventure.Adventure;
import com.tompy.directive.FeatureType;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.EntityFacade;
import com.tompy.entity.EntityService;
import com.tompy.entity.compartment.CompartmentImpl;
import com.tompy.entity.EntityBuilderHelperImpl;
import com.tompy.entity.EntityFacadeImpl;
import com.tompy.entity.item.Item;
import com.tompy.exit.Exit;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.tompy.attribute.Attribute.*;
import static com.tompy.directive.EventType.*;

public class FeatureBasicImpl extends CompartmentImpl implements Feature {
    private static final Logger LOGGER = LogManager.getLogger(FeatureBasicImpl.class);
    protected final List<Response> notImplemented;
    protected final EntityFacade open;
    protected final EntityFacade locked;
    protected final EntityFacade visible;
    protected int manipulationTicks;

    protected FeatureBasicImpl(Long key, String name, List<String> descriptors, String description,
            EntityService entityService, int manipulationTicks) {
        super(key, name, descriptors, description);
        notImplemented =
                Collections.singletonList(responseFactory.createBuilder().source(name).text("Not Implemented").build());
        open = EntityFacadeImpl.createBuilder(entityService).entity(this).attribute(OPEN).build();
        locked = EntityFacadeImpl.createBuilder(entityService).entity(this).attribute(LOCKED).build();
        visible = EntityFacadeImpl.createBuilder(entityService).entity(this).attribute(VISIBLE).build();
        this.manipulationTicks = manipulationTicks;
    }

    public static FeatureBuilder createBuilder(Long key, EntityService entityService) {
        return new FeatureBuilderImpl(key, entityService);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Response> search(Player player, Adventure adventure, EntityService entityService) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Searching Feature [{}]", getName());

        if (EntityUtil.is(visible)) {
            adventure.setActionTicks(manipulationTicks);
            returnValue.addAll(entityService.handle(this, EVENT_FEATURE_SEARCH, player, adventure));
        }

        return returnValue;
    }

    @Override
    public List<Response> open(Player player, Adventure adventure, EntityService entityService) {
        LOGGER.info("Opening [{}]", this.getName());
        List<Response> returnValue = new ArrayList<>();

        if (EntityUtil.is(visible)) {
            if (!EntityUtil.is(open) && !EntityUtil.is(locked)) {
                EntityUtil.add(open);
                adventure.setActionTicks(manipulationTicks);
                returnValue.addAll(entityService.handle(this, EVENT_FEATURE_OPEN, player, adventure));
                items.stream().forEach((i) -> entityService.add(i, VISIBLE));
            } else if (EntityUtil.is(locked)) {
                adventure.setActionTicks(manipulationTicks);
                returnValue
                        .addAll(entityService.handle(this, EVENT_FEATURE_OPEN_BUT_LOCKED, player, adventure));
            }
        }

        return returnValue;
    }

    @Override
    public List<Response> close(Player player, Adventure adventure, EntityService entityService) {
        LOGGER.info("Closing [{}]", this.getName());
        List<Response> returnValue = new ArrayList<>();

        if (EntityUtil.is(visible)) {
            if (EntityUtil.is(open)) {
                EntityUtil.remove(open);
                adventure.setActionTicks(manipulationTicks);
                returnValue.addAll(entityService.handle(this, EVENT_FEATURE_CLOSE, player, adventure));
            }
        }

        return returnValue;
    }

    @Override
    public List<Response> lock(Player player, Adventure adventure, EntityService entityService) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Locking [{}]", this.getName());

        if (EntityUtil.is(visible)) {
            if (!EntityUtil.is(open) && !EntityUtil.is(locked)) {
                EntityUtil.add(locked);
                adventure.setActionTicks(manipulationTicks);
                returnValue.addAll(entityService.handle(this, EVENT_FEATURE_LOCK, player, adventure));
            } else {
                adventure.setActionTicks(manipulationTicks);
                returnValue
                        .addAll(entityService.handle(this, EVENT_FEATURE_UNABLE_TO_LOCK, player, adventure));
            }
        }

        return returnValue;
    }

    @Override
    public List<Response> unlock(Player player, Adventure adventure, EntityService entityService) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Unlocking [{}]", this.getName());

        if (EntityUtil.is(visible)) {
            if (!EntityUtil.is(open) && EntityUtil.is(locked)) {
                EntityUtil.remove(locked);
                adventure.setActionTicks(manipulationTicks);
                returnValue.addAll(entityService.handle(this, EVENT_FEATURE_UNLOCK, player, adventure));
            } else {
                adventure.setActionTicks(manipulationTicks);
                returnValue.addAll(entityService
                        .handle(this, EVENT_FEATURE_UNABLE_TO_UNLOCK, player, adventure));
            }
        }

        return returnValue;
    }

    @Override
    public List<Response> misUse(Item item, Player player, Adventure adventure, EntityService entityService) {
        List<Response> returnValue = new ArrayList<>();
        if (item != null) {
            adventure.setActionTicks(manipulationTicks);
            returnValue.addAll(entityService.handle(this, EVENT_FEATURE_TRAP, player, adventure));
        }
        return returnValue;
    }

    @Override
    public List<Response> drink(Player player, Adventure adventure, EntityService entityService) {
        return notImplemented;
    }

    public static final class FeatureBuilderImpl extends EntityBuilderHelperImpl implements FeatureBuilder {
        private FeatureType type;
        private Exit exit;
        private int manipulationTicks = 0;

        public FeatureBuilderImpl(Long key, EntityService entityService) {
            super(key, entityService);
        }

        @Override
        public FeatureBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public FeatureBuilder description(String description) {
            this.description = description;
            return this;
        }

        @Override
        public FeatureBuilder type(FeatureType type) {
            this.type = type;
            return this;
        }

        @Override
        public FeatureBuilder exit(Exit exit) {
            this.exit = exit;
            return this;
        }

        @Override
        public FeatureBuilder manipulationTicks(int ticks) {
            this.manipulationTicks = ticks;
            return this;
        }

        @Override
        public Feature build() {
            switch (type) {
                case FEATURE_CHEST:
                    FeatureChestImpl chest =
                            new FeatureChestImpl(key, name == null ? "CHEST-" + key : name, this.buildDescriptors(),
                                    description, entityService, manipulationTicks);
                    if (entityService != null) {
                        entityService.addFeature(chest);
                    }
                    return chest;
                case FEATURE_DOOR:
                    FeatureDoorImpl door =
                            new FeatureDoorImpl(key, name == null ? "DOOR-" + key : name, this.buildDescriptors(),
                                    description, entityService, exit, manipulationTicks);
                    if (entityService != null) {
                        entityService.addFeature(door);
                    }
                    return door;
                case FEATURE_MONSTER:
                    FeatureMonsterImpl monster =
                            new FeatureMonsterImpl(key, name, this.buildDescriptors(), description, entityService, manipulationTicks);
                    if (entityService != null) {
                        entityService.addFeature(monster);
                    }
                    return monster;
                case FEATURE_TABLE:
                    FeatureTableImpl table =
                            new FeatureTableImpl(key, name == null ? "TABLE-" + key : name, this.buildDescriptors(),
                                    description, entityService, manipulationTicks);
                    if (entityService != null) {
                        entityService.addFeature(table);
                    }
                default:
                    FeatureBasicImpl feature =
                            new FeatureBasicImpl(key, name == null ? "FEATURE-" + key : name, this.buildDescriptors(),
                                    description, entityService, manipulationTicks);
                    if (entityService != null) {
                        entityService.addFeature(feature);
                    }
                    return feature;
            }
        }
    }
}
