package com.tompy.entity.encounter;

import com.tompy.entity.EntityService;
import com.tompy.entity.event.Event;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tompy.directive.EventType.EVENT_INTERACTION;
import static com.tompy.entity.encounter.EncounterConstants.BUY;
import static com.tompy.entity.encounter.EncounterConstants.SELL;

public class MerchantChat extends MerchantStateBaseImpl implements MerchantState {
    public static final Logger LOGGER = LogManager.getLogger(MerchantChat.class);

    public MerchantChat(Merchant merchant) {
        super(merchant);
    }

    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

    @Override
    public Map<Long, String> getOptions(EntityService entityService) {
        LOGGER.info("Getting options for Merchant chat state.");
        Map<Long, String> returnValue = new HashMap<>();
        for (Event event : entityService.get(merchant, EVENT_INTERACTION)) {
            if (event.pull(merchant.getPlayer(), merchant.getAdventure(), null)) {
                returnValue.put(event.getKey(), event.getDescription());
            }
        }
        returnValue.put((long) BUY, "Buy something");
        returnValue.put((long) SELL, "Sell something");
        return returnValue;
    }

    @Override
    public List<Response> act(Long option, EntityService entityService) {
        LOGGER.info("Merchant chat action.");
        int choice = option.intValue();
        if (choice == BUY || choice == SELL) {
            merchant.changeState(choice == BUY ? merchant.getBuyState() : merchant.getSellState());
        } else {

            for (Event event : entityService.get(merchant, EVENT_INTERACTION)) {
                if (event.getKey() == option) {
                    return event.apply(merchant.getPlayer(), merchant.getAdventure(), entityService);
                }
            }
        }
        return Collections.emptyList();
    }
}
