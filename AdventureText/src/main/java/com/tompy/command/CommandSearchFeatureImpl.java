package com.tompy.command;

import com.tompy.adventure.Adventure;
import com.tompy.attribute.Attribute;
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
import java.util.Optional;

public class CommandSearchFeatureImpl extends CommandSearchImpl {
    private static final Logger LOGGER = LogManager.getLogger(CommandSearchFeatureImpl.class);

    public CommandSearchFeatureImpl(CommandType type, String target,
            String secondaryTarget) {
        super(type, target, secondaryTarget);
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure, EntityService entityService) {
        LOGGER.info("Executing Search Feature");
        List<Response> returnValue = new ArrayList<>();

        Optional<Feature> optObject = EntityUtil
                .findVisibleFeatureByDescription(entityService, player.getArea().getAllFeatures(), target,
                        adventure.getUI());

        if (optObject.isPresent()) {
            Feature object = optObject.get();
            returnValue.addAll(object.search(player, adventure, entityService));
            if (entityService.is(object, Attribute.OPEN)) {
                object.getAllItems().stream().forEach((i) -> {
                    entityService.add(i, Attribute.VISIBLE);
                    returnValue
                            .add(responseFactory.createBuilder().source("CommandSearchFeature").text(i.getDescription())
                                    .build());


                });
            }
        }

        return returnValue;
    }
}