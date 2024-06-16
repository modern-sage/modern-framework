package com.modernframework.core.convert.multiple;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class StringToBlockingQueueConverter extends StringToIterableConverter<BlockingQueue> {

    @Override
    protected BlockingQueue createMultiValue(int size, Class<?> multiValueType) {
        return new ArrayBlockingQueue(size);
    }
}
