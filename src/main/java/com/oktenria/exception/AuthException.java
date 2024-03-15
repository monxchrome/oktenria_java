package com.oktenria.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.security.core.AuthenticationException;

public class AuthException extends AuthenticationException {


    public AuthException(String message, JwtException cause) {
        super(message, cause);
    }

    public AuthException(String message) {
        super(message);
    }
}
