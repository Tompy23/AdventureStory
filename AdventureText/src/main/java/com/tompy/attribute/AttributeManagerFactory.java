package com.tompy.attribute;

/**
 * Factory for creating the elements of an Attribute Manager
 */
public interface AttributeManagerFactory {

    /**
     * Create an Attribute Manager for each entity
     *
     * @return - a new Attribute Manager
     */
    AttributeManager create();
}
