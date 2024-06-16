package com.modernframework.core.convert.integerconvert;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author lzh
 * @date 2023/12/13 10:14
 * @since 1.0.0
 */
public class IntegerToLocalDateTime implements IntegerConverter<LocalDateTime>{

    @Override
    public LocalDateTime convert(Integer source) {
        return source != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(source), ZoneId.systemDefault()) : null;
    }
}
