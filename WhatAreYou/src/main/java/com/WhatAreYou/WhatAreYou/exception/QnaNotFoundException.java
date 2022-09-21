package com.WhatAreYou.WhatAreYou.exception;

public class QnaNotFoundException extends RuntimeException {

    private static final String MESSAGE = "질문이 존재하지 않습니다.";

    public QnaNotFoundException() {
        super(MESSAGE);
    }

    public QnaNotFoundException(String message) {
        super(message);
    }

    public QnaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public QnaNotFoundException(Throwable cause) {
        super(cause);
    }

    public QnaNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
