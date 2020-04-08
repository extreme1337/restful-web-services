package com.marko.rest.restfulwebservices.jwt.resources;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message, Throwable cause){
        super(message,cause);

    }
}
