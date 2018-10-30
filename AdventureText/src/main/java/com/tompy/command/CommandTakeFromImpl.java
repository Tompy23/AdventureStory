package com.tompy.command;

import com.tompy.adventure.Adventure;
import com.tompy.attribute.Attribute;
import com.tompy.directive.CommandType;
import com.tompy.entity.EntityService;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.feature.Feature;
import com.tompy.entity.item.Item;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CommandTakeFromImpl extends CommandTakeImpl {
    private static final Logger LOGGER = LogManager.getLogger(CommandTakeFromImpl.class);
    private final String item;

    protected CommandTakeFromImpl(CommandType type, String item, String target) {
        super(type != null ? type : CommandType.COMMAND_TAKE_FROM, target);
        this.item = Objects.requireNonNull(item, "Item cannot be null.");
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure, EntityService entityService) {
        LOGGER.info("Executing Command Take From");
        List<Response> returnValue = new ArrayList<>();

        Optional<Feature> optObject = EntityUtil
                .findVisibleFeatureByDescription(entityService, player.getArea().getAllFeatures(), target,
                        adventure.getUI());

        if (optObject.isPresent()) {
            Feature object = optObject.get();

            if (entityService.is(object, Attribute.VISIBLE)) {
                Optional<Item> optSource = EntityUtil
                        .findVisibleItemByDescription(entityService, object.getAllItems(), item, adventure.getUI());

                if (optSource.isPresent()) {
                    Item source = optSource.get();

                    if (player.addItem(source)) {
                        object.removeItem(source);
                        returnValue.add(responseFactory.createBuilder().source("CommandTake")
                                .text(String.format("%s now has %s", player.getName(), source.getDescription()))
                                .build());
                    } else {
                        // TODO Inventory full?  Or some other issue?
                        returnValue
                                .add(responseFactory.createBuilder().source("CommandTakeFrom").text("Inventory full.")
                                        .build());
                    }
                } else { // Item not in target
                    returnValue.add(responseFactory.createBuilder().source("CommandTakeFrom")
                            .text(String.format("%s not in %s", item.toLowerCase(), target.toLowerCase())).build());
                }
            } else { // target not visible
                returnValue.add(responseFactory.createBuilder().source("CommandTakeFrom")
                        .text(String.format("%s not here", target.toLowerCase())).build());
            }
        } else { // Target not present
            returnValue.add(responseFactory.createBuilder().source("CommandTakeFrom")
                    .text(String.format("%s does not contain %s", target.toLowerCase(), item.toLowerCase())).build());
        }

        return returnValue;
    }
}
