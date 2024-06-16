package com.modernframework.core.convert.shortconvert;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ShortToLocalDateTime implements ShortConverter<LocalDateTime> {
    @Override
    public LocalDateTime convert(Short source) {
        return source != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(source), ZoneId.systemDefault()) : null;
    }
}
