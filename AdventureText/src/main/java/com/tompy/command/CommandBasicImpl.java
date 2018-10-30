package com.tompy.command;

import com.tompy.directive.CommandType;
import com.tompy.entity.EntityService;
import com.tompy.response.Responsive;

import java.util.Objects;

import static com.tompy.directive.CommandType.COMMAND_QUIT;

public abstract class CommandBasicImpl extends Responsive implements Command {
    protected CommandType type = COMMAND_QUIT;

    protected CommandBasicImpl(CommandType type) {
        this.type = Objects.requireNonNull(type, "Type cannot be null.");
    }

    @Override
    public CommandType getType() {
        return type;
    }
}
