package com.modernframework.core.func;


import com.modernframework.core.utils.ExceptionUtils;

import java.util.function.Predicate;

/**
 * ThrowableSupplier <br/>
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @see Predicate
 * @since 1.0.0
 */
@FunctionalInterface
public interface ThrowableSupplier<T> {

    /**
     * Applies this function to the given argument
     *
     * @return the specified result
     * @throws Throwable if met the error
     */
    T get() throws Throwable;

    default T getWithRe() {
        return getThrowable(RuntimeException.class);
    }

    default <E extends Throwable> T getThrowable(Class<E> errType) throws E {
        T result;
        try {
            result = get();
        } catch (Throwable e) {
            throw ExceptionUtils.wrapThrowable(e, errType);
        }
        return result;
    }

    /**
     * Executes {@link ThrowableSupplier}
     *
     * @param supplier {@link ThrowableSupplier}
     * @param <T>      the supplied type
     * @return the result after execution
     * @throws RuntimeException
     */
    static <T> T execute(ThrowableSupplier<T> supplier) throws RuntimeException {
        return execute(supplier, RuntimeException.class);
    }

    static <T, E extends Throwable> T execute(ThrowableSupplier<T> supplier, Class<E> errType) throws E {
        return supplier.getThrowable(errType);
    }

}
