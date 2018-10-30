package com.tompy.entity.encounter;

import com.tompy.entity.EntityService;
import com.tompy.entity.item.Item;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tompy.attribute.Attribute.VALUE;
import static com.tompy.entity.encounter.EncounterConstants.NOTHING;

public class MerchantBuy extends MerchantStateBaseImpl implements MerchantState {
    public static final Logger LOGGER = LogManager.getLogger(MerchantBuy.class);

    public MerchantBuy(Merchant merchant) {
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
        LOGGER.info("Retrieving options for Buy state of Merchant.");
        Map<Long, String> returnValue = new HashMap<>();
        for (Item item : merchant.getAvailable()) {
            returnValue.put(item.getKey(), String.format("Buy %s for $%d.", item.getDescription(), (int) Math
                    .round(entityService.valueFor(item, VALUE).getAsInt() * merchant.getBuyRate())));
        }
        returnValue.put((long) NOTHING, "Nothing");
        return returnValue;
    }

    @Override
    public List<Response> act(Long option, EntityService entityService) {
        LOGGER.info("Player buys item from merchant.");
        Item tradeItem = null;
        for (Item item : merchant.getAvailable()) {
            if (item.getKey() == option) {
                tradeItem = item;
                break;
            }
        }
        if (tradeItem != null) {
            if (merchant.getPlayer().pay((int) Math
                    .round(entityService.valueFor(tradeItem, VALUE).getAsInt() *
                            merchant.getBuyRate()))) {
                merchant.removeItem(tradeItem);
                merchant.getPlayer().addItem(tradeItem);
                LOGGER.info("Item [{}] bought.", tradeItem.getDescription());
            }
        }
        merchant.changeState(merchant.getChatState());
        return Collections.emptyList();
    }
}
