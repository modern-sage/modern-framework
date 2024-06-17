package com.modernframework.base.vo;

/**
 * 可以被转换的VO
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public interface Convertible<S, T> {

     T convert(S source);

}
