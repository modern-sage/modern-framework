package com.modernframework.core.convert.localdatetimeconvert;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author lzh
 * @date 2023/12/13 10:14
 * @since 1.0.0
 */
public class LocalDateTimeToBigDecimal implements LocalDateTimeConvert<BigDecimal> {

    @Override
    public BigDecimal convert(LocalDateTime source) {
        return source != null ? BigDecimal.valueOf(Timestamp.valueOf(source).getTime()): null;
    }

}
