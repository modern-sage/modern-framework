package com.modernframework.core.utils.tuple;

import java.util.Objects;

/**
 * Tuple2 - 二元组类
 * 
 * 用于存储两个不同类型的值的不可变容器类。
 * Tuple2 提供了一种类型安全的方式将两个相关值组合在一起，
 * 避免了创建专门的数据类来存储临时成对数据。
 *
 * 用途示例：
 * - 在方法返回值中返回两个相关的结果
 * - 存储键值对
 * - 传递多个相关参数
 *
 * @param <T0> 第一个元素的类型
 * @param <T1> 第二个元素的类型
 * 
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public final class Tuple2<T0, T1> {

	/** 第一个元素 */
	public T0 _0;
	
	/** 第二个元素 */
	public T1 _1;

	/**
	 * 构造一个二元组
	 * 
	 * @param _0 第一个元素
	 * @param _1 第二个元素
	 */
	public Tuple2(T0 _0, T1 _1) {
		this._0 = _0;
		this._1 = _1;
	}

	/**
	 * 获取第一个元素
	 * 
	 * @return 第一个元素
	 */
	public T0 get_0() {
		return _0;
	}

	/**
	 * 设置第一个元素
	 * 
	 * @param _0 第一个元素的新值
	 */
	public void set_0(T0 _0) {
		this._0 = _0;
	}

	/**
	 * 获取第二个元素
	 * 
	 * @return 第二个元素
	 */
	public T1 get_1() {
		return _1;
	}

	/**
	 * 设置第二个元素
	 * 
	 * @param _1 第二个元素的新值
	 */
	public void set_1(T1 _1) {
		this._1 = _1;
	}

	/**
	 * 判断两个Tuple2是否相等
	 * 
	 * 两个Tuple2相等当且仅当它们的两个对应元素都相等
	 * 
	 * @param o 要比较的对象
	 * @return 如果相等返回true，否则返回false
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
            return true;
        }
		if (o instanceof Tuple2) {
			@SuppressWarnings("rawtypes")
			Tuple2 t = (Tuple2) o;
			return Objects.equals(t._0, _0) && Objects.equals(t._1, _1);
		}
		return false;
	}

	/**
	 * 计算Tuple2的哈希码
	 * 
	 * 哈希码基于两个元素的哈希码计算得出
	 * 
	 * @return 哈希码
	 */
	@Override
	public int hashCode() {
		return Objects.hash(_0, _1);
	}

	/**
	 * 返回Tuple2的字符串表示形式
	 * 
	 * 格式为：(元素1, 元素2)
	 * 
	 * @return 字符串表示
	 */
	@Override
	public String toString() {
		return "(" + _0 + ", " + _1 + ")";
	}

}
