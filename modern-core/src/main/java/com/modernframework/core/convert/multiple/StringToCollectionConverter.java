package com.modernframework.core.convert.multiple;

import java.util.ArrayList;
import java.util.Collection;

public class StringToCollectionConverter extends StringToIterableConverter<Collection> {

    @Override
    protected Collection createMultiValue(int size, Class<?> multiValueType) {
        return new ArrayList(size);
    }
}
