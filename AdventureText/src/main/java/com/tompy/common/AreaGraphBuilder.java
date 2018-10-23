package com.tompy.common;

import com.tompy.entity.area.Area;
import com.tompy.exit.Exit;

import java.util.List;

public interface AreaGraphBuilder extends Builder<AreaGraph> {
    AreaGraphBuilder areas(List<Area> areas);

    AreaGraphBuilder exits(List<Exit> exits);
}
