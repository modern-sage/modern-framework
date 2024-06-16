package com.modernframework.core.convert.multiple;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class StringToTransferQueueConverter extends StringToIterableConverter<TransferQueue> {

    @Override
    protected TransferQueue createMultiValue(int size, Class<?> multiValueType) {
        return new LinkedTransferQueue();
    }
}
