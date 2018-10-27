package com.tompy.exit;

import com.tompy.directive.Direction;
import com.tompy.entity.area.Area;
import com.tompy.response.Response;
import com.tompy.response.Responsive;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExitImpl extends Responsive implements Exit, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger(ExitImpl.class);
    private final Area[] areas;
    private boolean state;
    private final int passThruTicks;

    private ExitImpl(Area[] areas, boolean state, int ticks) {
        this.areas = areas;
        this.state = state;
        this.passThruTicks = ticks;
    }

    public static ExitBuilder createBuilder() {
        return new ExitBuilderImpl();
    }

    @Override
    public List<Response> passThru(Direction direction) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Attempting to pass thru exit [{}] connecting areas [{}] and [{}]",
                    new String[]{direction.name(), areas[0].getName(), areas[1].getName()});

        if (!state) {
            returnValue.add(
                responseFactory.createBuilder().text(String.format("Unable to move %s", direction.getDescription()))
                    .source("Exit").build());
        }

        return returnValue;
    }

    @Override
    public Area getConnectedArea(Area area) {
        if (area.getKey() == areas[0].getKey()) {
            return areas[1];
        } else if (area.getKey() == areas[1].getKey()) {
            return areas[0];
        }
        return null;
    }

    @Override
    public void open() {
        state = true;
    }

    @Override
    public void close() {
        state = false;
    }

    @Override
    public boolean isOpen() {
        return state;
    }

    @Override
    public int getPassThruTicks() {
        return passThruTicks;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.areas[0].getName());
        sb.append(" - ");
        sb.append(this.areas[1].getName());
        sb.append(" - ");
        sb.append(this.isOpen());

        return sb.toString();
    }

    public static class ExitBuilderImpl implements ExitBuilder {
        private List<Area> areas = new ArrayList<>();
        private boolean state;
        private int passThruTicks = 0;

        @Override
        public Exit build() {
            if (areas.size() == 2) {
                return new ExitImpl(areas.toArray(new Area[2]), state, passThruTicks);
            } else {
                return null;
            }
        }

        @Override
        public ExitBuilder area(Area area) {
            this.areas.add(area);
            return this;
        }

        @Override
        public ExitBuilder state(boolean state) {
            this.state = state;
            return this;
        }

        @Override
        public ExitBuilder passThruTicks(int ticks) {
            this.passThruTicks = ticks;
            return this;
        }
    }
}
