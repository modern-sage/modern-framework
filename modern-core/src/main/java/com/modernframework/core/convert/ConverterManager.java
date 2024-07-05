package com.modernframework.core.convert;

import com.modernframework.core.func.Streams;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * 转换器接口函数
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public abstract class ConverterManager {

    private static Map<Class<?>, Map<Class<?>, Converter>> converterMap;

    public static Converter<?, ?> getConverter(Class<?> sourceType, Class<?> targetType) {
        if (sourceType == null || targetType == null) {
            return null;
        }
        if (converterMap == null) {
            Map<Class<?>, Map<Class<?>, Converter>> initMap = new HashMap<>();
            Streams.stream(ServiceLoader.load(Converter.class))
                    .forEach(c -> {
                        Map<Class<?>, Converter> m = initMap.computeIfAbsent(c.getSourceType(), k -> new HashMap<>());
                        m.put(c.getTargetType(), c);
                    });
            converterMap = Collections.unmodifiableMap(initMap);
        }
        Map<Class<?>, Converter> map = converterMap.get(sourceType);
        if (map == null) {
            return null;
        } else {
            return map.get(targetType);
        }
    }

    public static <T> T convertIfPossible(Object source, Class<T> targetType) {
        if (source == null) {
            return null;
        }
        Converter converter = getConverter(source.getClass(), targetType);
        if (converter != null) {
            return (T) converter.convert(source);
        }
        return null;
    }

}
