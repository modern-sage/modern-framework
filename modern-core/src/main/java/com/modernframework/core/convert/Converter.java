package com.modernframework.core.convert;


import com.modernframework.core.func.Streams;
import com.modernframework.core.lang.Prioritized;
import com.modernframework.core.utils.ClassUtils;
import com.modernframework.core.utils.TypeUtils;

import java.util.ServiceLoader;
import java.util.function.Function;

/**
 * 转换器接口函数
 *
 * @param <S> The source type
 * @param <T> The target type
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface Converter<S, T> extends Function<S, T>, Prioritized {

    default boolean accept(Class<?> sourceType, Class<?> targetType) {
        return ClassUtils.isAssignableFrom(sourceType, getSourceType())
                && ClassUtils.isAssignableFrom(targetType, getTargetType());
    }

    T convert(S source);

    @Override
    default T apply(S source) {
        return convert(source);
    }

    /**
     * Get the source type
     *
     * @return non-null
     */
    default Class<S> getSourceType() {
        return TypeUtils.findActualTypeArgumentClass(getClass(), Converter.class, true, 0);
    }

    /**
     * Get the target type
     *
     * @return non-null
     */
    default Class<T> getTargetType() {
        return TypeUtils.findActualTypeArgumentClass(getClass(), Converter.class, true, 1);
    }

    /**
     * Get the Converter instance from {@link ServiceLoader} with the specified source and target type
     *
     * @param sourceType the source type
     * @param targetType the target type
     * @see ServiceLoader#load(Class)
     */
    static Converter<?, ?> getConverter(Class<?> sourceType, Class<?> targetType) {
        return Streams.stream(ServiceLoader.load(Converter.class))
                .filter(converter -> converter.accept(sourceType, targetType))
                .findFirst()
                .orElse(null);
    }

}
