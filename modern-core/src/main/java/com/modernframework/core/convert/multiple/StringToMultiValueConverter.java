package com.modernframework.core.convert.multiple;


import com.modernframework.core.utils.ArrayUtils;
import com.modernframework.core.utils.StringUtils;

public interface StringToMultiValueConverter extends MultiValueConverter<String> {

    @Override
    default Object convert(String source, Class<?> multiValueType, Class<?> elementType) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        String[] segments = StringUtils.split(source, ',');
        if (ArrayUtils.isEmpty(segments)) {
            segments = new String[]{source};
        }
        int size = segments.length;
        return convert(segments, size, multiValueType, elementType);
    }

    Object convert(String[] segments, int size, Class<?> targetType, Class<?> elementType);
}
