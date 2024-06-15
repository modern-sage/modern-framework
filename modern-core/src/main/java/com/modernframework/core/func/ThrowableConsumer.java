package com.modernframework.core.func;


import com.modernframework.core.utils.ExceptionUtils;

import java.util.function.Consumer;

/**
 * @Description A function interface for {@link Consumer} with {@link Throwable}
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@FunctionalInterface
public interface ThrowableConsumer<T> {

    /**
     * Performs this operation on the given argument.
     * @param t the input argument
     * @throws Throwable if met with error
     */
    void accept(T t) throws Throwable;

    /**
     * Executes {@link ThrowableConsumer}
     * @param t the input argument
     * @param consumer {@link ThrowableConsumer}
     * @throws RuntimeException wrap {@link Throwable} to the specified {@link RuntimeException}
     */
    static <T> void execute(T t, ThrowableConsumer<T> consumer) throws RuntimeException {
        execute(t, consumer, RuntimeException.class);
    }

    /**
     * Executes {@link ThrowableConsumer}
     * @param t the input argument
     * @param consumer {@link ThrowableConsumer}
     * @throws E wrap {@link Throwable} to the specified {@link Throwable} type
     */
    static <T, E extends Throwable> void execute(T t, ThrowableConsumer<T> consumer, Class<E> throwableType) throws E {
        try {
            consumer.accept(t);
        } catch (Throwable e) {
            throw ExceptionUtils.wrapThrowable(e, throwableType);
        }
    }

}
