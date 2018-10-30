package com.tompy.entity.feature;

import com.tompy.entity.EntityUtil;
import com.tompy.entity.EntityService;

import java.util.List;

import static com.tompy.attribute.Attribute.OPEN;
import static com.tompy.attribute.Attribute.VISIBLE;

public class FeatureTableImpl extends FeatureBasicImpl {
    protected FeatureTableImpl(Long key, String name, List<String> descriptors, String description,
            EntityService entityService, int manipulationTicks) {
        super(key, name, descriptors, description, manipulationTicks);
        entityService.add(this, OPEN);
        entityService.add(this, VISIBLE);
    }
}
