package com.tompy.attribute;

public class AttributeInfo {
    private Integer value;
    private String doesApply;
    private String doesNotApply;

    public AttributeInfo(Integer value, String doesApply, String doesNotApply) {
        this.value = value;
        this.doesApply = doesApply;
        this.doesNotApply = doesNotApply;
    }

    public AttributeInfo(String doesApply, String doesNotApply) {
        this(null, doesApply, doesNotApply);
    }

    public AttributeInfo(Integer value) {
        this(value, null, null);
    }


    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDoesApply() {
        return doesApply;
    }

    public void setDoesApply(String doesApply) {
        this.doesApply = doesApply;
    }

    public String getDoesNotApply() {
        return doesNotApply;
    }

    public void setDoesNotApply(String doesNotApply) {
        this.doesNotApply = doesNotApply;
    }


}
