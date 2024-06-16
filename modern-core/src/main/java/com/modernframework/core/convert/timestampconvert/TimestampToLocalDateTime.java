package com.modernframework.core.convert.timestampconvert;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TimestampToLocalDateTime implements TimestampConverter<LocalDateTime> {

    /**
     * Convert the source-typed value to the target-typed value
     *
     * @param source the source-typed value
     * @return the target-typed value
     */
    @Override
    public LocalDateTime convert(Timestamp source) {
        if(source == null) {
            return null;
        }
        return source.toLocalDateTime();
    }
}
