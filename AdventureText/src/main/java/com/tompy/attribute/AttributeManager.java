package com.tompy.attribute;

import java.util.OptionalInt;
import java.util.Set;

/**
 * Manages attributes of type Attribute for an entity.
 * This manager knows how an attribute works with respect to its properties
 * like stackable.
 */
public interface AttributeManager {

    /**
     * Clear the managed Attributes
     */
    void clear();

    /**
     * Add an Attribute to the manager.  If it exists, it will do nothing.
     * Handles specific properties of an attribute when adding.
     *
     * @param attribute - the Attribute to be added.
     */
    void add(Attribute attribute);

    /**
     * Add an Attribute to the manager and set its value if appropriate.
     * Handles specific properties when adding.  If it exists, it will do
     * nothing.
     *
     * @param attribute - the Attribute to be added.
     * @param value     - the value to set.
     */
    void add(Attribute attribute, Integer value);

    /**
     * Removes an Attribute from the manager.
     * Handles specific properties like reducing an Attribute from
     * stackables, etc.
     *
     * @param attribute - the Attribute to remove
     */
    void remove(Attribute attribute);

    /**
     * Resets the value to 0 if appropriate, otherwise does nothing.
     *
     * @param attribute - the Attribute to reset
     */
    void reset(Attribute attribute);

    /**
     * Retrieve a List of all attributes managed for this entity
     *
     * @return - A List of Attributes
     */
    Set<Attribute> getAll();

    /**
     * Retrieve the value associated with an Attribute if one exists.
     *
     * @param attribute - the Attribute whose value will be returned
     * @return - an Optional which will contain the value if an Attribute has
     * a value.
     */
    OptionalInt getValue(Attribute attribute);

    /**
     * Determine if this attribute is present
     *
     * @param attribute - The attribute to check
     * @return - whether or not the attribute is present
     */
    boolean is(Attribute attribute);

    /**
     * Add a String for Response parsing when the attribute applies
     *
     * @param attribute
     * @param text
     */
    void addApply(Attribute attribute, String text);

    /**
     * Add a String for Repsonse parsing when the attribute does not apply
     * @param attribute
     * @param text
     */
    void addDesNotApply(Attribute attribute, String text);

    /**
     * Determines if the attribute applies or not and return appropriate String
     *
     * @param attribute
     * @param apply
     * @return
     */
    String getApplication(Attribute attribute, boolean apply);
}
