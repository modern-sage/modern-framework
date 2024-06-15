package com.modernframework.core.constant;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 基本变量类型的枚举<br>
 * 基本类型枚举包括原始类型和包装类型
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public enum BasicType {
	BYTE, SHORT, INT, INTEGER, LONG, DOUBLE, FLOAT, BOOLEAN, CHAR, CHARACTER, STRING;

	/** 包装类型为Key，原始类型为Value，例如： Integer.class =》 int.class. */
	public static final Map<Class<?>, Class<?>> WRAPPER_PRIMITIVE_MAP;
	/** 原始类型为Key，包装类型为Value，例如： int.class =》 Integer.class. */
	public static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_MAP;

	static {
		Map<Class<?>, Class<?>> wrapperMap = new HashMap<>(8);
		Map<Class<?>, Class<?>> primitiveMap = new HashMap<>(8);
		wrapperMap.put(Boolean.class, boolean.class);
		wrapperMap.put(Byte.class, byte.class);
		wrapperMap.put(Character.class, char.class);
		wrapperMap.put(Double.class, double.class);
		wrapperMap.put(Float.class, float.class);
		wrapperMap.put(Integer.class, int.class);
		wrapperMap.put(Long.class, long.class);
		wrapperMap.put(Short.class, short.class);

		for (Map.Entry<Class<?>, Class<?>> entry : wrapperMap.entrySet()) {
			primitiveMap.put(entry.getValue(), entry.getKey());
		}
		WRAPPER_PRIMITIVE_MAP = Collections.unmodifiableMap(wrapperMap);
		PRIMITIVE_WRAPPER_MAP = Collections.unmodifiableMap(primitiveMap);
	}

	/**
	 * 原始类转为包装类，非原始类返回原类
	 * @param clazz 原始类
	 * @return 包装类
	 */
	public static Class<?> wrap(Class<?> clazz){
		if(null == clazz || false == clazz.isPrimitive()){
			return clazz;
		}
		Class<?> result = PRIMITIVE_WRAPPER_MAP.get(clazz);
		return (null == result) ? clazz : result;
	}

	/**
	 * 包装类转为原始类，非包装类返回原类
	 * @param clazz 包装类
	 * @return 原始类
	 */
	public static Class<?> unWrap(Class<?> clazz){
		if(null == clazz || clazz.isPrimitive()){
			return clazz;
		}
		Class<?> result = WRAPPER_PRIMITIVE_MAP.get(clazz);
		return (null == result) ? clazz : result;
	}
}
