package com.modernframework.core.func;

import java.io.Serializable;

@FunctionalInterface
public interface SerialFunction<P, R> extends Serializable {

    R call(P parameter) throws Exception;

    default R callWithRuntimeException(P parameter) {
        try {
            return call(parameter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
