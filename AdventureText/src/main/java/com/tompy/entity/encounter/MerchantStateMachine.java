package com.tompy.entity.encounter;

/**
 * Specific state machine for Merchant encounter
 */
public interface MerchantStateMachine {

    void process();
    /**
     * Change Merchant State
     *
     * @param newState
     */
    void changeState(MerchantState newState);
}
