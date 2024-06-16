package com.modernframework.core.convert.multiple;

import java.util.HashSet;
import java.util.Set;

public class StringToSetConverter extends StringToIterableConverter<Set> {

    @Override
    protected Set createMultiValue(int size, Class<?> multiValueType) {
        return new HashSet(size);
    }
}
