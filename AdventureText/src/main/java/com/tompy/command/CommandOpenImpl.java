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

import static com.tompy.directive.CommandType.COMMAND_OPEN;

public class CommandOpenImpl extends CommandBasicImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CommandOpenImpl.class);
    private final String target;

    private CommandOpenImpl(CommandType type, String target, EntityService entityService) {
        super(type, entityService);
        this.target = Objects.requireNonNull(target, "Target cannot be null.");
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandOpenImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandOpenImpl.CommandOpenBuilderImpl();
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure) {
        LOGGER.info("Executing Command Open");
        List<Response> returnValue = new ArrayList<>();

        Optional<Feature> optObject = EntityUtil
                .findVisibleFeatureByDescription(entityService, player.getArea().getAllFeatures(), target,
                        adventure.getUI());

        if (optObject.isPresent()) {
            Feature object = optObject.get();
            returnValue.addAll(object.open(player, adventure));
        }

        return returnValue;
    }

    public static final class CommandOpenBuilderImpl extends CommandBuilderImpl {
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
            return new CommandOpenImpl(COMMAND_OPEN, target, entityService);
        }
    }
}
