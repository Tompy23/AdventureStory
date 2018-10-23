package com.tompy.entity.event;

import com.tompy.entity.Entity;

import java.util.Objects;

public abstract class TriggerImpl implements Trigger {
    protected boolean trigger;
    protected final Entity entity;

    public TriggerImpl(Entity entity) {
        this.entity = Objects.requireNonNull(entity, "Entity cannot be null.");
    }
}
