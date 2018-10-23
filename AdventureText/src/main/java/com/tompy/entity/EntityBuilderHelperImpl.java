package com.tompy.entity;

import com.tompy.entity.EntityService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class helps with building Entity subclasses.
 */
public abstract class EntityBuilderHelperImpl {
    protected final EntityService entityService;
    protected final Long key;
    protected String name;
    protected String description;

    public EntityBuilderHelperImpl(Long key, EntityService entityService) {
        this.key = Objects.requireNonNull(key, "Entity Key cannot be null.");
        this.entityService = entityService;
    }

    /**
     * Takes the name and pulls it apart to build the descriptors
     *
     * @return - a list of words used to describe the entity and for searching
     */
    protected List<String> buildDescriptors() {
        List<String> descriptors = new ArrayList<>();
        if (description != null) {
            descriptors.addAll(Arrays.asList(description.split(" ")));
        }

        return descriptors;
    }
}
