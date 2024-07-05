package com.modernframework.core.utils;

import com.modernframework.core.func.ThrowableSupplier;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * 异常工具类
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public abstract class ExceptionUtils {

    /**
     * 获得完整消息，包括异常名，消息格式为：{SimpleClassName}: {ThrowableMessage}
     *
     * @param e 异常
     * @return 完整消息
     */
    public static String getMessage(Throwable e) {
        if (null == e) {
            return StringUtils.NULL;
        }
        return String.format("%s: %s", e.getClass().getSimpleName(), e.getMessage());
    }

    /**
     * 将 {@link Throwable source} 转为 {@link Throwable exceptionType exception}
     *
     * @param source        源异常
     * @param exceptionType 目标异常类
     */
    public static <T extends Throwable> T wrapThrowable(Throwable source, Class<T> exceptionType) {
        String message = source.getMessage();
        Throwable cause = source.getCause();

        Constructor<?>[] constructors = exceptionType.getConstructors();

        if (constructors.length == 0) {
            throw new IllegalArgumentException("The exceptionType must have one public constructor.");
        }

        Arrays.sort(constructors, (o1, o2) -> Integer.compare(o2.getParameterCount(), o1.getParameterCount()));

        // 找到参数最多的那个构造方法
        Constructor<?> constructor = constructors[0];
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        int parameterTypesLength = parameterTypes.length;
        Object[] parameters = new Object[parameterTypesLength];
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            if (String.class.isAssignableFrom(parameterType)) {
                parameters[i] = message;
            }
            if (Throwable.class.isAssignableFrom(parameterType)) {
                parameters[i] = cause;
            }
        }

        return ThrowableSupplier.execute(() -> (T) constructor.newInstance(parameters));
    }

}
