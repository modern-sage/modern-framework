package com.modernframework.core.func;

import com.modernframework.core.utils.ExceptionUtils;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * ThrowableAction <br/>
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @see Predicate
 * @since 1.0.0
 */
@FunctionalInterface
public interface ThrowableAction {

    /**
     * Executes the action
     *
     * @throws Throwable if met with error
     */
    void execute() throws Throwable;

    /**
     * Execute {@link ThrowableAction}
     * @param action {@link ThrowableAction}
     * @throws RuntimeException wrap {@link Exception} to {@link RuntimeException}
     */
    static void execute(ThrowableAction action) throws RuntimeException {
        execute(action, RuntimeException.class);
    }

    /**
     * 执行函数{@link ThrowableAction action}，对异常使用 {@link Consumer handleThrowable}来进行处理
     * @param action 执行函数{@link ThrowableAction action}
     * @param handleThrowable 处理异常函数 {@link Consumer handleThrowable}
     */
    static void execute(ThrowableAction action, Consumer<Throwable> handleThrowable) {
        try {
            action.execute();
        } catch (Throwable e) {
            handleThrowable.accept(e);
        }
    }

    /**
     * Executes {@link ThrowableAction}
     * @param action {@link ThrowableAction}
     * @param throwableType {@link Throwable} to the specified {@link Throwable} type
     */
    static <T extends Throwable> void execute(ThrowableAction action, Class<T> throwableType) throws T {
        try {
            action.execute();
        } catch (Throwable throwable) {
            throw ExceptionUtils.wrapThrowable(throwable, throwableType);
        }
    }

}
