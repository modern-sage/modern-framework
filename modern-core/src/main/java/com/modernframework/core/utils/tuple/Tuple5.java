package com.modernframework.core.utils.tuple;

import java.util.Objects;

/**
 * Tuple5 - 五元组类
 * 
 * 用于存储五个不同类型的值的不可变容器类。
 * Tuple5 提供了一种类型安全的方式将五个相关值组合在一起，
 * 避免了创建专门的数据类来存储临时成组数据。
 *
 * 用途示例：
 * - 在方法返回值中返回五个相关的结果
 * - 存储包含五个属性的数据组合
 * - 传递多个相关参数
 *
 * @param <T0> 第一个元素的类型
 * @param <T1> 第二个元素的类型
 * @param <T2> 第三个元素的类型
 * @param <T3> 第四个元素的类型
 * @param <T4> 第五个元素的类型
 * 
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public final class Tuple5<T0, T1, T2, T3, T4> {

	/** 第一个元素 */
	public T0 _0;
	
	/** 第二个元素 */
	public T1 _1;
	
	/** 第三个元素 */
	public T2 _2;
	
	/** 第四个元素 */
	public T3 _3;
	
	/** 第五个元素 */
	public T4 _4;

	/**
	 * 构造一个五元组
	 * 
	 * @param _0 第一个元素
	 * @param _1 第二个元素
	 * @param _2 第三个元素
	 * @param _3 第四个元素
	 * @param _4 第五个元素
	 */
	public Tuple5(T0 _0, T1 _1, T2 _2, T3 _3, T4 _4) {
		this._0 = _0;
		this._1 = _1;
		this._2 = _2;
		this._3 = _3;
		this._4 = _4;
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
	 * 获取第三个元素
	 * 
	 * @return 第三个元素
	 */
	public T2 get_2() {
		return _2;
	}

	/**
	 * 设置第三个元素
	 * 
	 * @param _2 第三个元素的新值
	 */
	public void set_2(T2 _2) {
		this._2 = _2;
	}

	/**
	 * 获取第四个元素
	 * 
	 * @return 第四个元素
	 */
	public T3 get_3() {
		return _3;
	}

	/**
	 * 设置第四个元素
	 * 
	 * @param _3 第四个元素的新值
	 */
	public void set_3(T3 _3) {
		this._3 = _3;
	}

	/**
	 * 获取第五个元素
	 * 
	 * @return 第五个元素
	 */
	public T4 get_4() {
		return _4;
	}

	/**
	 * 设置第五个元素
	 * 
	 * @param _4 第五个元素的新值
	 */
	public void set_4(T4 _4) {
		this._4 = _4;
	}

	/**
	 * 判断两个Tuple5是否相等
	 * 
	 * 两个Tuple5相等当且仅当它们的五个对应元素都相等
	 * 
	 * @param o 要比较的对象
	 * @return 如果相等返回true，否则返回false
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
            return true;
        }
		if (o instanceof Tuple5) {
			@SuppressWarnings("rawtypes")
			Tuple5 t = (Tuple5) o;
			return Objects.equals(t._0, _0) && Objects.equals(t._1, _1)
					&& Objects.equals(t._2, _2) && Objects.equals(t._3, _3) && Objects.equals(t._4, _4);
		}
		return false;
	}

	/**
	 * 计算Tuple5的哈希码
	 * 
	 * 哈希码基于五个元素的哈希码计算得出
	 * 
	 * @return 哈希码
	 */
	@Override
	public int hashCode() {
		return Objects.hash(_0, _1, _2, _3, _4);
	}

	/**
	 * 返回Tuple5的字符串表示形式
	 * 
	 * 格式为：(元素1, 元素2, 元素3, 元素4, 元素5)
	 * 
	 * @return 字符串表示
	 */
	@Override
	public String toString() {
		return "(" + _0 + ", " + _1 + ", " + _2 + ", " + _3 + ", " + _4 + ")";
	}

}
