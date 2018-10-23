package com.tompy.entity;

import java.util.List;

public interface Entity {

    /**
     * Retrieve the Entity Key
     *
     * @return - the value of the Key
     */
    Long getKey();

    /**
     * Get the simple name of the Item
     *
     * @return - Item's simple name
     */
    String getName();

    /**
     * Get the full descriptive name of the Item
     *
     * @return - Item's long name
     */
    String getDescription();

    /**
     * The descriptive words taken from the name
     *
     * @return - List of descriptive words for Item
     */
    List<String> getDescriptionWords();

    /**
     * Helper function for creating messages about the Entity
     *
     * @return - Representation of the source entity
     */
    String getSource();

}
