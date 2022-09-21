package com.WhatAreYou.WhatAreYou.exception;

public class FollowMemberCanNotSameException extends RuntimeException {
    public FollowMemberCanNotSameException() {
    }

    public FollowMemberCanNotSameException(String message) {
        super(message);
    }

    public FollowMemberCanNotSameException(String message, Throwable cause) {
        super(message, cause);
    }

    public FollowMemberCanNotSameException(Throwable cause) {
        super(cause);
    }

    public FollowMemberCanNotSameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
