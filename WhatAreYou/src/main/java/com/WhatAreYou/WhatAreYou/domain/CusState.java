package com.WhatAreYou.WhatAreYou.domain;

public enum CusState {
    READY("Ready"),
    WORK("Work"),
    CHECK("Check"),
    DONE("Done");

    private final String displayValue;

    private CusState(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
