package com.tompy.exit;

public class ExitBuilderFactoryImpl implements ExitBuilderFactory {
    @Override
    public ExitBuilder builder() {
        return new ExitImpl.ExitBuilderImpl();
    }
}
