package com.tompy.exit;

import com.tompy.common.Builder;
import com.tompy.entity.area.Area;

public interface ExitBuilder extends Builder<Exit> {
    ExitBuilder area(Area area);

    ExitBuilder state(boolean state);

    ExitBuilder passThruTicks(int ticks);
}
