package com.tompy.entity.encounter;

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

public class MerchantSell extends MerchantStateBaseImpl implements MerchantState {
    public static final Logger LOGGER = LogManager.getLogger(MerchantSell.class);

    public MerchantSell(Merchant merchant) {
        super(merchant);
    }


    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

    @Override
    public Map<Long, String> getOptions() {
        LOGGER.info("Getting options for player selling to Merchant.");
        Map<Long, String> returnValue = new HashMap<>();
        for (Item item : merchant.getPlayer().getInventory()) {
            if (merchant.getEntityService().is(item, VALUE)) {
                returnValue.put(item.getKey(), String.format("Sell %s for $%d.", item.getDescription(), (int) Math
                        .round(merchant.getEntityService().valueFor(item, VALUE).getAsInt() * merchant.getSellRate())));
            }
        }
        returnValue.put((long) NOTHING, "Nothing");
        return returnValue;
    }

    @Override
    public List<Response> act(Long option) {
        LOGGER.info("Acting on Merchant Sell state.");
        Item tradeItem = null;
        for (Item item : merchant.getPlayer().getInventory()) {
            if (item.getKey() == option) {
                tradeItem = item;
            }
        }
        if (tradeItem != null) {
            merchant.getAvailable().add(tradeItem);
            merchant.getPlayer().removeItem(tradeItem);
            merchant.getPlayer().addMoney(merchant.getEntityService().valueFor(tradeItem, VALUE).getAsInt());
            LOGGER.info("Player sold [{}] to merchant.", tradeItem.getDescription());
        }
        merchant.changeState(merchant.getChatState());
        return Collections.emptyList();
    }
}
