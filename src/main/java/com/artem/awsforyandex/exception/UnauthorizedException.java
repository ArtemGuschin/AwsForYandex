package com.artem.awsforyandex.exception;

public class UnauthorizedException extends ApiException {

    public UnauthorizedException(String message) {
        super(message, "Artem Exception unauthorized");
    }
}
