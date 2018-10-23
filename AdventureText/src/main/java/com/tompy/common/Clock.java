package com.tompy.common;

public interface Clock {
    int getCurrentTicks();
    void setActionTicks(int ticks);
    int getCurrentActionTicks();
    void endAction();
}
