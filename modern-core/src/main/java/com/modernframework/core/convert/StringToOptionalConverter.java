package com.modernframework.core.convert;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public class StringToOptionalConverter implements StringConverter<Optional> {

    @Override
    public Optional convert(String source) {
        return ofNullable(source);
    }


    @Override
    public int getPriority() {
        return MIN_PRIORITY;
    }
}
