package com.modernframework.core.convert;

import com.modernframework.core.utils.ClassLoaderUtils;
import com.modernframework.core.utils.ClassUtils;
import com.modernframework.core.utils.StringUtils;

public class StringToClassConverter implements StringConverter<Class<?>> {

    @Override
    public Class<?> convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        ClassLoader classLoader = ClassLoaderUtils.getClassLoader(getClass());
        return ClassUtils.resolveClass(source, classLoader);
    }
}
