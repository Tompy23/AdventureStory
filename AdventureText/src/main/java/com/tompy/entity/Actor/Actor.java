package com.tompy.entity.Actor;

import com.tompy.adventure.Adventure;
import com.tompy.entity.area.Area;
import com.tompy.entity.compartment.Compartment;
import com.tompy.player.Player;
import com.tompy.response.Response;

import java.util.List;

public interface Actor extends Compartment {

    List<Response> takeAction(Player player, Adventure adventure);

    void assignMoveStrategy(MoveStrategy moveStrategy);

    List<Response> move(Player player, Adventure adventure);

    /**
     * Get the current area
     *
     * @return
     */
    Area getArea();

    /**
     * Set the current area
     *
     * @param area
     */
    void setArea(Area area);
}
