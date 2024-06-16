package com.modernframework.core.convert.byteconvert;


import com.modernframework.core.convert.Converter;
import com.modernframework.core.utils.TypeUtils;

/**
 * byte 转换器接口
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
@FunctionalInterface
public interface ByteConvert<T> extends Converter<Byte, T> {

    @Override
    default Class<Byte> getSourceType() {
        return Byte.class;
    }

    @Override
    default Class<T> getTargetType() {
        return TypeUtils.findActualTypeArgumentClass(getClass(), ByteConvert.class, 0);
    }
}
