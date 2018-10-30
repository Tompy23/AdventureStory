package com.tompy.command;

import com.tompy.adventure.Adventure;
import com.tompy.adventure.AdventureUtils;
import com.tompy.directive.CommandType;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.EntityService;
import com.tompy.entity.feature.Feature;
import com.tompy.entity.item.Item;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class CommandUseImpl extends CommandBasicImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CommandUseImpl.class);
    private final String subject;
    private final String target;

    public CommandUseImpl(CommandType type, EntityService entityService, String subject, String target) {
        super(type, entityService);
        this.subject = Objects.requireNonNull(subject, "Subject cannot be null.");
        this.target = target;
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandUseImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandUseImpl.CommandUseBuilderImpl();
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure) {
        LOGGER.info("Executing Command Use. subject: {}; target {}", subject, target);
        Optional<Item> optSource = EntityUtil.findItemByDescription(player.getInventory(), subject, adventure.getUI());

        if (target == null) {
            return subjectOnlyUse(player, adventure, optSource);
        } else {
            List<Response> returnValue = new ArrayList<>();
            Optional<Feature> optObject = EntityUtil
                    .findVisibleFeatureByDescription(entityService, player.getArea().getAllFeatures(), target,
                            adventure.getUI());

            if (optSource.isPresent() && optObject.isPresent()) {
                Item source = optSource.get();
                Feature object = optObject.get();

                // Use subject on target
                if (source.hasTarget(object)) {
                    LOGGER.info("Using [{}] on [{}]", source.getName(), object.getName());
                    returnValue.addAll(source.use(player, adventure, entityService));
                } else {
                    returnValue.addAll(source.misUse(object, player, adventure, entityService));
                    returnValue.addAll(object.misUse(source, player, adventure, entityService));
                }

            } else {
                if (optSource.isPresent()) {
                    returnValue.addAll(optSource.get().misUse(null, player, adventure, entityService));
                } else {
                    returnValue.addAll(optObject.get().misUse(null, player, adventure, entityService));
                }
            }

            return returnValue;
        }
    }

    private List<Response> subjectOnlyUse(Player player, Adventure adventure, Optional<Item> optSource) {
        if (optSource.isPresent()) {
            Item source = optSource.get();
            return source.use(player, adventure, entityService);
        }

        return Collections.emptyList();
    }

    public static final class CommandUseBuilderImpl extends CommandBuilderImpl {
        private String target;
        private String subject;


        @Override
        public Command build() {
            return new CommandUseImpl(type, entityService, subject, target);
        }

        @Override
        public CommandBuilder parts(String[] parts) {
            subject = "";
            target = "";
            String[] commands = AdventureUtils.parseCommand(parts, Arrays.asList(new String[]{"on", "in"}));
            if (commands.length == 2) {
                subject = commands[0];
                target = commands[1];
            } else if (commands.length == 1) {
                subject = commands[0];
                target = null;
            }

            return this;
        }
    }
}
