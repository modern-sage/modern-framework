package com.modernframework.core.convert.multiple;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class StringToQueueConverter extends StringToIterableConverter<Queue> {

    @Override
    protected Queue createMultiValue(int size, Class<?> multiValueType) {
        return new ArrayDeque(size);
    }
}
