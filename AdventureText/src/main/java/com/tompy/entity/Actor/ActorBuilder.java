package com.tompy.entity.Actor;

import com.tompy.common.Builder;

public interface ActorBuilder extends Builder<Actor> {
    ActorBuilder name(String name);

    ActorBuilder description(String description);
}
