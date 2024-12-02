package com.modernframework.core.func;

import java.io.Serializable;
import java.util.function.Predicate;

/**
 * SerialFunction <br/>
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @see Predicate
 * @since 1.0.0
 */
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
