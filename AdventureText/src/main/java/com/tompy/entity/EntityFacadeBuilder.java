package com.tompy.entity;

import com.tompy.attribute.Attribute;
import com.tompy.common.Builder;

public interface EntityFacadeBuilder extends Builder<EntityFacade> {

    EntityFacadeBuilder entity(Entity entity);

    EntityFacadeBuilder attribute(Attribute attribute);
}
