package com.tompy.entity.Actor;

import com.tompy.adventure.Adventure;
import com.tompy.entity.Entity;
import com.tompy.entity.EntityService;
import com.tompy.entity.event.ActionImpl;
import com.tompy.player.Player;
import com.tompy.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ActionActorMove extends ActionImpl {
    private final Actor actor;

    public ActionActorMove(Entity entity, EntityService entityService, String[] responses, Actor actor) {
        super(entity, entityService, responses);
        this.actor = Objects.requireNonNull(actor, "Actor cannot be null.");
    }

    @Override
    public List<Response> apply(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        returnValue.addAll(responses.stream().
                map((r) -> responseFactory.createBuilder().source(source).text(substitution(r)).build())
                .collect(Collectors.toList()));
        returnValue.addAll(actor.move(player, adventure));
        return returnValue;

    }
}
