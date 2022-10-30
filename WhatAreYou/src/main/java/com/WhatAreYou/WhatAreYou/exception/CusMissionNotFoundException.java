package com.WhatAreYou.WhatAreYou.exception;

public class CusMissionNotFoundException extends RuntimeException {
    private static final String MESSAGE = "커미션이 존재하지 않습니다.";

    public CusMissionNotFoundException() {
        super();
    }

    public CusMissionNotFoundException(String message) {
        super(message);
    }

    public CusMissionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CusMissionNotFoundException(Throwable cause) {
        super(cause);
    }

    public CusMissionNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
