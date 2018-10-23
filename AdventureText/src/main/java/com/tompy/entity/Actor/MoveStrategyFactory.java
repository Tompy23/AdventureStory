package com.tompy.entity.Actor;

import com.tompy.directive.MoveStrategyType;

public interface MoveStrategyFactory {
    MoveStrategy createMoveStrategy(Actor actor, MoveStrategyType type);
}
