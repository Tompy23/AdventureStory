package com.tompy.entity.encounter;

import com.tompy.response.Response;

import java.util.List;
import java.util.Map;

/**
 * A state description for a merchant encounter
 */
public interface MerchantState {

    /**
     * Start the Merchant encounter state
     */
    void start();

    /**
     * End the Merchant encounter state
     */
    void end();

    /**
     * Retrieve the options for the merchant encounter
     *
     * @return
     */
    Map<Long, String> getOptions();

    /**
     * Action on the chosen option
     *
     * @param option
     * @return
     */
    List<Response> act(Long option);
}
