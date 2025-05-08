package com.modernframework.core.convert;


import com.modernframework.core.func.Streams;
import com.modernframework.core.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

/**
 * 转换器
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public abstract class ConvertUtils {

    private static final Logger log = LoggerFactory.getLogger(ConvertUtils.class);

    public static <T> T convertIfPossible(Object source, Class<T> targetType, T defaultValue) {
        return convert(source, targetType, true, defaultValue);
    }

    public static <T> T convertIfPossible(Object source, Class<T> targetType) {
        return convert(source, targetType, true, null);
    }

    public static <T> T convert(Object source, Class<T> targetType) {
        return convert(source, targetType, false, null);
    }

    public static <T> T convert(Object source, Class<T> targetType, boolean ignoreException, T defaultValue) {
        if (source == null) {
            return defaultValue;
        }

        Class<?> actualType = targetType;
        if (targetType.isPrimitive()) {
            actualType = ClassUtils.primitiveToWrapper(targetType);
        }

        if (source.getClass().equals(actualType) || actualType.isAssignableFrom(source.getClass())) {
            return (T) source;
        }
        T result = null;
        Converter converter = Converter.getConverter(source.getClass(), actualType);
        if (converter == null) {
            String error = String.format("未找到对应的转换器, 源: %s, 目标: %s", source.getClass(), actualType);
            if(log.isDebugEnabled()) {
                List<String> impls = Streams.stream(ServiceLoader.load(Converter.class)).map(x -> x.getClass().getName())
                        .toList();
                log.debug("Convert impl classes: {}", impls);
            }
            if (ignoreException) {
                log.error("未找到对应的转换器, 源: {}, 目标: {}", source.getClass(), actualType);
            } else {
                throw new UnsupportedOperationException(error);
            }
        } else {
            result = (T) converter.convert(source);
        }
        return Optional.ofNullable(result).orElse(defaultValue);
    }
}
