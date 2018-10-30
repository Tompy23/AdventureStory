package com.tompy.command;

import com.tompy.adventure.Adventure;
import com.tompy.directive.CommandType;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.EntityService;
import com.tompy.entity.feature.Feature;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.tompy.directive.CommandType.COMMAND_CLOSE;

public class CommandCloseImpl extends CommandBasicImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CommandCloseImpl.class);
    private final String target;

    private CommandCloseImpl(CommandType type, String target) {
        super(type);
        this.target = Objects.requireNonNull(target, "Target cannot be null.");
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandCloseImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandCloseImpl.CommandCloseBuilderImpl();
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure, EntityService entityService) {
        LOGGER.info("Executing Command Close");
        List<Response> returnValue = new ArrayList<>();

        Optional<Feature> objectOpt = EntityUtil
                .findVisibleFeatureByDescription(entityService, player.getArea().getAllFeatures(), target,
                        adventure.getUI());

        if (objectOpt.isPresent()) {
            Feature object = objectOpt.get();
            LOGGER.debug("Converted {} to {}", new String[]{target, object.getName()});
            returnValue.addAll(object.close(player, adventure, entityService));
        } else {
            LOGGER.debug("Unable to convert {}", target);
        }
        return returnValue;
    }

    public static final class CommandCloseBuilderImpl extends CommandBuilderImpl {
        private String target;

        @Override
        public CommandBuilder parts(String[] parts) {
            StringBuilder targetSb = new StringBuilder();
            for (int i = 1; i < parts.length; i++) {
                targetSb.append(parts[i] + " ");
            }
            target = targetSb.toString().trim();
            return this;
        }

        @Override
        public Command build() {
            return new CommandCloseImpl(COMMAND_CLOSE, target);
        }
    }
}
