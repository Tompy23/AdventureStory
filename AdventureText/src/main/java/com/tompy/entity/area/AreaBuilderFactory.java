package com.tompy.entity.area;

import com.tompy.entity.area.Area;
import com.tompy.entity.area.AreaBuilder;

public interface AreaBuilderFactory {
    AreaBuilder createAreaBuilder();

    void addArea(Area area);
}
