package com.modernframework.core.anno;

import java.lang.annotation.*;

/**
 * 可以为null的注解标记
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 0.2.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface Nullable {
}
