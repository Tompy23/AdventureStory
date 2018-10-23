package com.tompy.entity.feature;

public interface FeatureBuilderFactory {
    FeatureBuilder createFeatureBuilder();

    void addFeature(Feature feature);
}
