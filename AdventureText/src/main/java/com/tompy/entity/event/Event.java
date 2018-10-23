package com.tompy.entity.event;

import com.tompy.entity.Entity;

/**
 * Compound Interface that aggregates the Action and Trigger Interfaces.
 */
public interface Event extends Action, Trigger, Entity {
}
