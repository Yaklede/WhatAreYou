package com.WhatAreYou.WhatAreYou.exception;

public class BoardNotFoundException extends RuntimeException {

    private static final String MESSAGE = "게시글이 존재하지 않습니다.";

    public BoardNotFoundException() {
        super(MESSAGE);
    }


    public BoardNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardNotFoundException(Throwable cause) {
        super(cause);
    }

    public BoardNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
