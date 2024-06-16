package com.modernframework.core.convert.multiple;

import com.modernframework.core.convert.Converter;
import com.modernframework.core.convert.ConverterManager;

import java.lang.reflect.Array;

public class StringToArrayConverter implements StringToMultiValueConverter {

    public boolean accept(Class<String> type, Class<?> multiValueType) {
        if (multiValueType != null && multiValueType.isArray()) {
            return true;
        }
        return false;
    }

    @Override
    public Object convert(String[] segments, int size, Class<?> targetType, Class<?> elementType) {

        Class<?> componentType = targetType.getComponentType();

        Converter converter = ConverterManager.getConverter(String.class, componentType);

        Object array = Array.newInstance(componentType, size);

        for (int i = 0; i < size; i++) {
            Array.set(array, i, converter.convert(segments[i]));
        }

        return array;
    }


    @Override
    public int getPriority() {
        return MIN_PRIORITY;
    }
}
