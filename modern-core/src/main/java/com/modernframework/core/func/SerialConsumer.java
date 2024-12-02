package com.modernframework.core.func;

import java.util.function.Predicate;

/**
 * SerialConsumer <br/>
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @see Predicate
 * @since 1.0.0
 */
public interface SerialConsumer<P> {

    void call(P parameter) throws Exception;

    default void callWithRuntimeException(P parameter){
        try {
            call(parameter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
