package com.modernframework.core.convert.multiple;


import com.modernframework.core.convert.ConverterManager;
import com.modernframework.core.convert.StringConverter;
import com.modernframework.core.utils.ClassUtils;
import com.modernframework.core.utils.TypeUtils;

import java.util.Collection;
import java.util.Optional;

import static com.modernframework.core.constant.StrConstant.BRACKET_END;
import static com.modernframework.core.constant.StrConstant.BRACKET_START;

public abstract class StringToIterableConverter<T extends Iterable> implements StringToMultiValueConverter {

    public boolean accept(Class<String> type, Class<?> multiValueType) {
        return ClassUtils.isAssignableFrom(getSupportedType(), multiValueType);
    }

    @Override
    public final Object convert(String[] segments, int size, Class<?> multiValueType, Class<?> elementType) {

        Optional<StringConverter> stringConverter = getStringConverter(elementType);

        return stringConverter.map(converter -> {

            T convertedObject = createMultiValue(size, multiValueType);

            if (convertedObject instanceof Collection) {
                Collection collection = (Collection) convertedObject;
                for (int i = 0; i < size; i++) {
                    String segment = segments[i];
                    if(i == 0 && segment.startsWith(BRACKET_START)) {
                        segment = segment.substring(1);
                    } else if (i == size - 1 && segment.endsWith(BRACKET_END)) {
                        segment = segment.substring(0, segment.length() - 1);
                    }
                    Object element = converter.convert(segment);
                    collection.add(element);
                }
                return collection;
            }

            return convertedObject;
        }).orElse(null);
    }

    protected abstract T createMultiValue(int size, Class<?> multiValueType);

    protected Optional<StringConverter> getStringConverter(Class<?> elementType) {
        StringConverter converter = (StringConverter) ConverterManager.getConverter(String.class, elementType);
        return Optional.ofNullable(converter);
    }

    protected final Class<T> getSupportedType() {
        return TypeUtils.findActualTypeArgumentClass(getClass(), StringToIterableConverter.class, 0);
    }

    @Override
    public final int getPriority() {
        int level = ClassUtils.getAllInterfaces(getSupportedType(), type ->
                ClassUtils.isAssignableFrom(Iterable.class, type)).size();
        return MIN_PRIORITY - level;
    }
}
