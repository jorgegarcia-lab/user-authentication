package com.bci.exercise.user_authentication.exception;

public enum ErrorCodes {

    SYS_ERROR_CODE(505, "An error occurred. Please try again"),
    VALUES_NOT_FORMATTED(503, ""),
    OBJECT_ALREADY_EXISTS(500, "An user with that email is already registered, please login."),
    USER_NON_EXIST(404, "User not found"),
    BAD_CREDENTIALS(403, "Bad credentials");

    private final int code;
    private final String message;

    ErrorCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() { return this.code; }

    public String message() { return this.message; }

}
