package com.tompy.command;

public interface CommandFactory {
    Command createCommand(String[] inputs);
}
