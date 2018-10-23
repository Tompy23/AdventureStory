package com.tompy.entity.Actor;

import com.tompy.directive.Direction;

public interface MoveStrategy {
    /**
     * Calculate the direction of the next move in the strategy
     *
     * @return - a legal Direction, otherwise null
     */
    Direction getMove();
}
