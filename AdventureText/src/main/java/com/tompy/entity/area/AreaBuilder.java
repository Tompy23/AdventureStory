package com.tompy.entity.area;

import com.tompy.common.Builder;
import com.tompy.common.Coordinates;

public interface AreaBuilder extends Builder<Area> {
    AreaBuilder name(String name);

    AreaBuilder description(String description);

    AreaBuilder searchDescription(String searchDesription);

    AreaBuilder compartmentName(String compartmentName);

    AreaBuilder compartmentDescription(String compartmentDescription);

    AreaBuilder searchTicks(int searchTicks);

    AreaBuilder coordinates(Coordinates coordinates);
}
