package com.tompy.entity.Actor;

public interface ActorBuilderFactory {
    ActorBuilder createActorBuilder();

    void addActor(Actor actor);
}
