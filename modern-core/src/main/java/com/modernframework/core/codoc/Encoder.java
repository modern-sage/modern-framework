package com.modernframework.core.codoc;

/**
 * 编码接口
 *
 * @param <T> 被编码的数据类型
 * @param <R> 编码后的数据类型
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface Encoder<T, R> {

	/**
	 * 执行编码
	 *
	 * @param data 被编码的数据
	 * @return 编码后的数据
	 */
	R encode(T data);
}
