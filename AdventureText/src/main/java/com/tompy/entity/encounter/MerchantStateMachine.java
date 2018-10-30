package com.tompy.entity.encounter;

import com.tompy.entity.EntityService;

/**
 * Specific state machine for Merchant encounter
 */
public interface MerchantStateMachine {

    void process(EntityService entityService);
    /**
     * Change Merchant State
     *
     * @param newState
     */
    void changeState(MerchantState newState);
}
