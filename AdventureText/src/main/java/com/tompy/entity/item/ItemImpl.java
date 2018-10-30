package com.tompy.entity.item;

import com.tompy.adventure.Adventure;
import com.tompy.directive.EventType;
import com.tompy.directive.ItemType;
import com.tompy.entity.*;
import com.tompy.entity.event.Event;
import com.tompy.entity.feature.Feature;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.tompy.attribute.Attribute.*;

public class ItemImpl extends EntityImpl implements Item {
    private static final Logger LOGGER = LogManager.getLogger(ItemImpl.class);
    protected final EntityFacade visible;
    protected final EntityFacade hands;
    protected final EntityFacade encumbrance;
    protected int manipulationTicks;

    protected ItemImpl(Long key, String name, List<String> descriptors, String description, EntityService entityService,
            int manipulationTicks) {
        super(key, name, descriptors, description);
        visible = EntityFacadeImpl.createBuilder(entityService).entity(this).attribute(VISIBLE).build();
        hands = EntityFacadeImpl.createBuilder(entityService).entity(this).attribute(HANDS).build();
        encumbrance = EntityFacadeImpl.createBuilder(entityService).entity(this).attribute(ENCUMBRANCE).build();
        this.manipulationTicks = manipulationTicks;
    }

    public static ItemBuilder createBuilder(Long key, EntityService entityService) {
        return new ItemBuilderImpl(key, entityService);
    }

    @Override
    public List<Response> use(Player player, Adventure adventure, EntityService entityService) {
        List<Response> returnValue = new ArrayList<>();

        LOGGER.info("Using [{}]", getName());

        // TODO placeholder so Item isn't so lonely
        // From this it appears Item may become abstract and its use(), etc. will be overloaded.
        returnValue.add(this.responseFactory.createBuilder().source(getSource()).text("Using " + getName()).build());

        return returnValue;
    }

    @Override
    public boolean hasTarget(Entity entity) {
        // TODO Looks like this might be overloaded as well for each item type.  Should ItemImpl be abstract?
        return false;
    }

    @Override
    public int hands() {
        return EntityUtil.valueFor(hands).getAsInt();
    }

    @Override
    public int encumbrance() {
        return EntityUtil.valueFor(encumbrance).getAsInt();
    }

    @Override
    public List<Response> misUse(Feature feature, Player player, Adventure adventure, EntityService entityService) {
        List<Response> returnValue = new ArrayList<>();
        return returnValue;
    }

    public static class ItemBuilderImpl extends EntityBuilderHelperImpl implements ItemBuilder {
        private ItemType type;
        private EntityFacade target;
        private Feature targetFeature;
        private Event event;
        private EventType eventType;
        private int manipulationTicks = 0;

        public ItemBuilderImpl(Long key, EntityService entityService) {
            super(key, entityService);
        }

        @Override
        public Item build() {
            LOGGER.info("Building item [{}]", key);
            Item item;
            switch (type) {
                case ITEM_KEY:
                    item = new ItemKeyImpl(key, name, buildDescriptors(), description, entityService, targetFeature,
                            manipulationTicks);
                    break;
                case ITEM_GEM:
                    item = new ItemGemImpl(key, name, buildDescriptors(), description, entityService,
                            manipulationTicks);
                    break;
                case ITEM_POTION:
                    item = new ItemPotionImpl(key, name, buildDescriptors(), description, entityService, event,
                            manipulationTicks);
                    break;
                case ITEM_WEAPON:
                    item = new ItemWeaponImpl(key, name, buildDescriptors(), description, entityService, targetFeature,
                            manipulationTicks);
                    break;
                default:
                    item = new ItemImpl(key, name, buildDescriptors(), description, entityService, manipulationTicks);
            }

            if (item != null && entityService != null) {
                entityService.addItem(item);
            }
            return item;
        }

        @Override
        public ItemBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public ItemBuilder description(String description) {
            this.description = description;
            return this;
        }

        @Override
        public ItemBuilder type(ItemType type) {
            this.type = type;
            return this;
        }

        @Override
        public ItemBuilder target(EntityFacade target) {
            this.target = target;
            return this;
        }

        @Override
        public ItemBuilder targetFeature(Feature targetFeature) {
            this.targetFeature = targetFeature;
            return this;
        }

        @Override
        public ItemBuilder event(Event event) {
            this.event = event;
            return this;
        }

        @Override
        public ItemBuilder eventType(EventType eventType) {
            this.eventType = eventType;
            return this;
        }

        @Override
        public ItemBuilder manipulationTicks(int ticks) {
            this.manipulationTicks = ticks;
            return this;
        }
    }
}
