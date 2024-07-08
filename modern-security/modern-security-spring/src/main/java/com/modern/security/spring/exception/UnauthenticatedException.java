package com.modern.security.spring.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * UnauthenticatedException
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class UnauthenticatedException extends AuthenticationException {

    public UnauthenticatedException(String msg) {
        super(msg);
    }

    public UnauthenticatedException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
