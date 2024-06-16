package com.modernframework.core.convert.multiple;

import java.util.ArrayList;
import java.util.List;

public class StringToListConverter extends StringToIterableConverter<List> {

    @Override
    protected List createMultiValue(int size, Class<?> multiValueType) {
        return new ArrayList(size);
    }
}
