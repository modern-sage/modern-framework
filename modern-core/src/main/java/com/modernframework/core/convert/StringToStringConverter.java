package com.modernframework.core.convert;

public class StringToStringConverter implements StringConverter<String> {

    @Override
    public String convert(String source) {
        return source;
    }
}
