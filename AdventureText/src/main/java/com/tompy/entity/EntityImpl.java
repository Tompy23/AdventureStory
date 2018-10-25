package com.tompy.entity;

import com.tompy.response.Responsive;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Provides basic implementation for Entity subclasses
 */
public abstract class EntityImpl extends Responsive implements Entity, Serializable {
    protected final String name;
    protected final List<String> descriptors;
    protected final String description;
    protected final EntityService entityService;
    private final Long key;

    public EntityImpl(Long key, String name, List<String> descriptors, String description,
        EntityService entityService) {
        this.key = Objects.requireNonNull(key, "Entity Key cannot be null.");
        this.name = name != null ? name : "E-" + key;
        this.descriptors = Objects.requireNonNull(descriptors, "Descriptors can be empty, but not null.");
        this.description = description;
        this.entityService = entityService;
    }

    @Override
    public Long getKey() {
        return key;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<String> getDescriptionWords() {
        return descriptors;
    }

    @Override
    public String getSource() {
        return name + "[" + getKey() + "]";
    }

    @Override
    public boolean equals(Object other) {
        Entity otherEntity = other instanceof Entity ? ((Entity) other) : null;
        if (otherEntity != null) {
            return this.key == otherEntity.getKey();
        }
        return false;
    }
}
