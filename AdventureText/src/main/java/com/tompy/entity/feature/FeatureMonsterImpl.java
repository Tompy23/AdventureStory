package com.tompy.entity.feature;

import com.tompy.entity.EntityUtil;
import com.tompy.entity.EntityService;

import java.util.List;

public class FeatureMonsterImpl extends FeatureBasicImpl {

    protected FeatureMonsterImpl(Long key, String name, List<String> descriptors, String description,
            EntityService entityService, int manipulationTicks) {
        super(key, name, descriptors, description, entityService, manipulationTicks);
        EntityUtil.add(visible);
    }
}
