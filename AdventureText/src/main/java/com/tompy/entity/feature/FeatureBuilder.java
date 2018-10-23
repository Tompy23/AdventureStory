package com.tompy.entity.feature;

import com.tompy.common.Builder;
import com.tompy.directive.FeatureType;
import com.tompy.exit.Exit;

public interface FeatureBuilder extends Builder<Feature> {

    FeatureBuilder name(String name);

    FeatureBuilder description(String description);

    FeatureBuilder type(FeatureType type);

    FeatureBuilder exit(Exit exit);

    FeatureBuilder manipulationTicks(int ticks);
}
