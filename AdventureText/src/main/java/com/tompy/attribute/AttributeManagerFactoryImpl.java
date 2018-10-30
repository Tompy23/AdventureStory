package com.tompy.attribute;

import java.io.Serializable;

public class AttributeManagerFactoryImpl implements AttributeManagerFactory, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public AttributeManager create() {
        return new AttributeManagerImpl();
    }
}
