package com.modernframework.core.utils.tuple;


/**
 * Tuples - 元组工具类
 * 
 * 提供工厂方法来创建不同长度的元组对象。
 * 这些工厂方法简化了元组对象的创建过程，
 * 避免了直接调用构造函数的繁琐代码。
 *
 * 用途示例：
 * - 快速创建二元组：Tuples.of("key", "value")
 * - 快速创建三元组：Tuples.of("name", 25, true)
 * - 快速创建四元组或五元组
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class Tuples {

	/**
	 * 创建一个二元组
	 * 
	 * @param <T0> 第一个元素的类型
	 * @param <T1> 第二个元素的类型
	 * @param _0 第一个元素
	 * @param _1 第二个元素
	 * @return 二元组对象
	 */
	public static <T0, T1> Tuple2<T0, T1> of(T0 _0, T1 _1) {
		return new Tuple2<>(_0, _1);
	}

	/**
	 * 创建一个三元组
	 * 
	 * @param <T0> 第一个元素的类型
	 * @param <T1> 第二个元素的类型
	 * @param <T2> 第三个元素的类型
	 * @param _0 第一个元素
	 * @param _1 第二个元素
	 * @param _2 第三个元素
	 * @return 三元组对象
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> of(T0 _0, T1 _1, T2 _2) {
		return new Tuple3<>(_0, _1, _2);
	}

	/**
	 * 创建一个四元组
	 * 
	 * @param <T0> 第一个元素的类型
	 * @param <T1> 第二个元素的类型
	 * @param <T2> 第三个元素的类型
	 * @param <T3> 第四个元素的类型
	 * @param _0 第一个元素
	 * @param _1 第二个元素
	 * @param _2 第三个元素
	 * @param _3 第四个元素
	 * @return 四元组对象
	 */
	public static <T0, T1, T2, T3> Tuple4<T0, T1, T2, T3> of(T0 _0, T1 _1, T2 _2, T3 _3) {
		return new Tuple4<>(_0, _1, _2, _3);
	}

	/**
	 * 创建一个五元组
	 * 
	 * @param <T0> 第一个元素的类型
	 * @param <T1> 第二个元素的类型
	 * @param <T2> 第三个元素的类型
	 * @param <T3> 第四个元素的类型
	 * @param <T4> 第五个元素的类型
	 * @param _0 第一个元素
	 * @param _1 第二个元素
	 * @param _2 第三个元素
	 * @param _3 第四个元素
	 * @param _4 第五个元素
	 * @return 五元组对象
	 */
	public static <T0, T1, T2, T3, T4> Tuple5<T0, T1, T2, T3, T4> of(T0 _0, T1 _1, T2 _2, T3 _3, T4 _4) {
		return new Tuple5<>(_0, _1, _2, _3, _4);
	}

}
