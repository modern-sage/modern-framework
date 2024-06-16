package com.modernframework.core.convert.bigdecimalconvert;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author lzh
 * @date 2023/12/13 14:15
 * @since 1.0.0
 */
public class BigDecimalToLocalDateTime implements BigDecimalConvert<LocalDateTime>{

    @Override
    public LocalDateTime convert(BigDecimal source) {
        return source != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(source.longValue()), ZoneId.systemDefault()) : null;
    }
}
