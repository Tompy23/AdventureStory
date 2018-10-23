package com.tompy.entity.feature;

import com.tompy.entity.EntityUtil;
import com.tompy.entity.EntityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * A Chest is a feature that can be open/close, locked, etc.
 */
public class FeatureChestImpl extends FeatureBasicImpl {
    private static final Logger LOGGER = LogManager.getLogger(FeatureChestImpl.class);

    protected FeatureChestImpl(Long key, String name, List<String> descriptors, String description,
            EntityService entityService, int manipulationTicks) {
        super(key, name, descriptors, description, entityService, manipulationTicks);
        EntityUtil.add(visible);
    }
}
