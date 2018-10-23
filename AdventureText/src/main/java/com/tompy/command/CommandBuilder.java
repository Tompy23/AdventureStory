package com.tompy.command;

import com.tompy.common.Builder;
import com.tompy.directive.CommandType;
import com.tompy.entity.EntityService;

/**
 * Basic properties for a Command
 */
public interface CommandBuilder extends Builder<Command> {

    /**
     * All the parts of a command as an Array of String
     * @param parts
     * @return
     */
    CommandBuilder parts(String[] parts);

    /**
     * The CommandType of a command for reference
     *
     * @param type
     * @return
     */
    CommandBuilder type(CommandType type);

    /**
     * The EntityService for use by the command
     *
     * @param entityService
     * @return
     */
    CommandBuilder entityService(EntityService entityService);
}
