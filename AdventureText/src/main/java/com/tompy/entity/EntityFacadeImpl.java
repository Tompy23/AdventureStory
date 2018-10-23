package com.tompy.entity;

import com.tompy.attribute.Attribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class EntityFacadeImpl implements EntityFacade {
    private static final Logger LOGGER = LogManager.getLogger(EntityFacadeImpl.class);
    private final Entity entity;
    private final Attribute attribute;
    private final EntityService entityService;

    private EntityFacadeImpl(Entity entity, Attribute attribute, EntityService entityService) {
        this.entity = Objects.requireNonNull(entity, "Entity cannot be null.");
        this.attribute = Objects.requireNonNull(attribute, "Attribute cannot be null.");
        this.entityService = Objects.requireNonNull(entityService, "Entity Service cannot be null.");
    }

    @Override
    public EntityService getService() {
        return entityService;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public Attribute getAttribute() {
        return attribute;
    }

    public static EntityFacadeBuilder createBuilder(EntityService entityService) {
        return new EntityFacadeBuilderImpl(entityService);
    }

    public static final class EntityFacadeBuilderImpl implements EntityFacadeBuilder {
        private Entity entity;
        private Attribute attribute;
        private EntityService entityService;

        public EntityFacadeBuilderImpl(EntityService entityService) {
            this.entityService = Objects.requireNonNull(entityService, "Entity Service cannot be null.");
        }

        @Override
        public EntityFacade build() {
            return new EntityFacadeImpl(entity, attribute, entityService);
        }

        @Override
        public EntityFacadeBuilder entity(Entity entity) {
            this.entity = entity;
            return this;
        }

        @Override
        public EntityFacadeBuilder attribute(Attribute attribute) {
            this.attribute = attribute;
            return this;
        }
    }
}
