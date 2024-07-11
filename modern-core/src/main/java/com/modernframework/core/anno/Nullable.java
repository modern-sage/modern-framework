package com.modernframework.core.anno;

import java.lang.annotation.*;

/**
 * The annotated element could be null under some circumstances.
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 0.2.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface Nullable {
}
