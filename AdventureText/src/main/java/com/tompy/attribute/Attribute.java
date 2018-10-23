package com.tompy.attribute;

/**
 * An enumeration of all the attributes available for entities.
 */
public enum Attribute {
    TEST_NORMALA(false, false, "TEST A", "APPLIES", "DOES NOT APPLY"),
    TEST_NORMALB(false, false, "TEST B", "APPLIES", "DOES NOT APPLY"),
    TEST_STACKABLE(true, false, "TEST STACKABLE", "APPLIES", "DOES NOT APPLY"),
    TEST_HAS_VALUE(false, true, "TEST HAS VALUE", "APPLIES", "DOES NOT APPLY"),
    BONUS(false, true, "Bonus", "bonus", "no bonus"),
    ENCUMBRANCE(false, true, "Encumbrance", "encumbrance", "no encumbrance"),
    HANDS(false, true, "Hands", "hands", "no hands"), LOCKED(false, false, "Locked", "locked", "unlocked"),
    OPEN(false, false, "Open", "an open", "a closed"), TAKABLE(false, false, "Takable", "can take", "can not take"),
    TARGETABLE(false, false, "Targetable", "can target", "can not target"),
    USABLE(false, false, "Usable", "usable", "not usable"), VALUE(false, true, "Value", "value of", "no value"),
    VISIBLE(false, false, "Visible", "see", "do not see");


    /**
     * A stackable Attribute is one that when added increments the value by 1,
     * and reduces the value by 1 when removed.  Zero is the lowest value.
     */
    private boolean stackable;

    /**
     * Allows the attribute to contain a value which is set when added.
     * The value is an Integer.
     */
    private boolean hasValue;

    /**
     * The printable name of the attribute
     */
    private String name;

    /**
     * The printable verbiage if the attribute applies
     */
    private String doesApply;

    /**
     * The printable verbiage if the attribute does not apply
     */
    private String doesNotApply;

    Attribute(boolean stackable, boolean hasValue, String name, String doesApply, String doesNotApply) {
        this.stackable = stackable;
        this.hasValue = hasValue;
        this.name = name;
        this.doesApply = doesApply;
        this.doesNotApply = doesNotApply;
    }

    public boolean stackable() {
        return stackable;
    }

    public boolean hasValue() {
        return hasValue;
    }

    public String getName() {
        return name;
    }

    public String getDoesApply() {
        return doesApply;
    }

    public String getDoesNotApply() {
        return doesNotApply;
    }
}
