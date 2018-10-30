package com.tompy.entity.encounter;

import java.io.Serializable;
import java.util.Objects;

public abstract class MerchantStateBaseImpl implements Serializable {
    private static final long serialVersionUID = 1L;
    protected final Merchant merchant;

    public MerchantStateBaseImpl(Merchant merchant) {
        this.merchant = Objects.requireNonNull(merchant, "Merchant cannot be null.");
    }
}
