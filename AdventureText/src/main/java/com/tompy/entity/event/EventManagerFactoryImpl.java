package com.tompy.entity.event;

import java.io.Serializable;

public class EventManagerFactoryImpl implements EventManagerFactory, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public EventManager create() {
        return new EventManagerImpl();
    }
}
