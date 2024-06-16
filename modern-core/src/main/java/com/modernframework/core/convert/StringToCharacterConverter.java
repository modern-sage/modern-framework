package com.modernframework.core.convert;

import com.modernframework.core.utils.StringUtils;

public class StringToCharacterConverter implements StringConverter<Character> {

    @Override
    public Character convert(String source) {
        int length = StringUtils.length(source);
        if (length == 0) {
            return null;
        }
        if (length > 1) {
            throw new IllegalArgumentException("The source String is more than one character!");
        }
        return source.charAt(0);
    }

    @Override
    public int getPriority() {
        return NORMAL_PRIORITY + 8;
    }
}
