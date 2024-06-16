package com.modernframework.core.convert.longconvert;

import java.math.BigDecimal;

/**
 * @author lzh
 * @date 2023/12/13 10:14
 * @since 1.0.0
 */
public class LongToBigDecimal implements LongConvert<BigDecimal>{

    @Override
    public BigDecimal convert(Long source) {
        return source != null ? BigDecimal.valueOf(source) : null ;
    }
}
