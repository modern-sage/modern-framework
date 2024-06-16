package com.modernframework.core.convert.longconvert;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author lzh
 * @date 2023/12/13 10:14
 * @since 1.0.0
 */
public class LongToLocalDateTime implements LongConvert<LocalDateTime>{

    @Override
    public LocalDateTime convert(Long source) {
        return source != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(source), ZoneId.systemDefault()) : null;
    }
}
