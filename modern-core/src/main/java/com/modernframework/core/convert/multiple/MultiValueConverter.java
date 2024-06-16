package com.modernframework.core.convert.multiple;



import com.modernframework.core.lang.Prioritized;
import com.modernframework.core.utils.TypeUtils;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;


public interface MultiValueConverter<S> extends Prioritized {

    boolean accept(Class<S> sourceType, Class<?> multiValueType);

    Object convert(S source, Class<?> multiValueType, Class<?> elementType);

    default Class<S> getSourceType() {
        return TypeUtils.findActualTypeArgumentClass(getClass(), MultiValueConverter.class, 0);
    }

    static MultiValueConverter<?> find(Class<?> sourceType, Class<?> targetType) {
        return StreamSupport.stream(ServiceLoader.load(MultiValueConverter.class).spliterator(), false)
                .filter(converter -> converter.accept(sourceType, targetType))
                .findFirst()
                .orElse(null);
    }

    static <T> T convertIfPossible(Object source, Class<?> multiValueType, Class<?> elementType) {
        if (source == null) {
            return null;
        }
        Class<?> sourceType = source.getClass();
        MultiValueConverter converter = find(sourceType, multiValueType);
        if (converter != null) {
            return (T) converter.convert(source, multiValueType, elementType);
        }
        return null;
    }
}
