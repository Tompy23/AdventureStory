package com.tompy.entity;

import com.tompy.attribute.Attribute;

/**
 * A convenience interface for interacting with Entities and their attributes.
 */
public interface EntityFacade {

    /**
     * Retrieve the entity service to use
     *
     * @return
     */
    EntityService getService();

    /**
     * Get the entity
     *
     * @return
     */
    Entity getEntity();

    /**
     * Get the attribute
     *
     * @return
     */
    Attribute getAttribute();
}
