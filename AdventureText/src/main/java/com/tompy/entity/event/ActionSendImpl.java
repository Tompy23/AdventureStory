package com.tompy.entity.event;

import com.tompy.adventure.Adventure;
import com.tompy.directive.Direction;
import com.tompy.entity.Entity;
import com.tompy.entity.EntityService;
import com.tompy.entity.area.Area;
import com.tompy.player.Player;
import com.tompy.response.Response;

import java.util.List;
import java.util.Objects;

public class ActionSendImpl extends ActionImpl {
    private final Area area;
    private final Direction direction;

    public ActionSendImpl(Entity entity, EntityService entityService, String[] responses, Area area,
            Direction direction) {
        super(entity, entityService, responses);
        this.area = Objects.requireNonNull(area, "Area cannot be null.");
        this.direction = Objects.requireNonNull(direction, "Direction cannot be null.");
    }

    @Override
    public List<Response> apply(Player player, Adventure adventure) {
        player.setArea(area);
        return area.enter(direction, player, adventure);
    }
}
