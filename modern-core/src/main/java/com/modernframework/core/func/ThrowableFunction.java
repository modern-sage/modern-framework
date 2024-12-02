package com.modernframework.core.func;


import com.modernframework.core.utils.ExceptionUtils;

import java.util.function.Predicate;

/**
 * ThrowableFunction <br/>
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @see Predicate
 * @since 1.0.0
 */
@FunctionalInterface
public interface ThrowableFunction<T, R> {

    /**
     * Applies the function to the given argument.
     * @param t the function argument
     * @return
     */
    R apply(T t) throws Throwable;

    static <T, R> R execute(T t, ThrowableFunction<T, R> throwableFunction) throws RuntimeException{
        return execute(t, throwableFunction, RuntimeException.class);
    }

    static <T, R, E extends Throwable> R execute(T t, ThrowableFunction<T, R> throwableFunction, Class<E> expClass) throws E{
        R result = null;
        try {
            result = throwableFunction.apply(t);
        } catch (Throwable e) {
            throw ExceptionUtils.wrapThrowable(e, expClass);
        }
        return result;
    }

}
