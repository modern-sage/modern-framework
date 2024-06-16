package com.modernframework.core.utils.tuple;

public class Tuples {

	public static <T0, T1> Tuple2<T0, T1> of(T0 _0, T1 _1) {
		return new Tuple2<>(_0, _1);
	}

	public static <T0, T1, T2> Tuple3<T0, T1, T2> of(T0 _0, T1 _1, T2 _2) {
		return new Tuple3<>(_0, _1, _2);
	}

	public static <T0, T1, T2, T3> Tuple4<T0, T1, T2, T3> of(T0 _0, T1 _1, T2 _2, T3 _3) {
		return new Tuple4<>(_0, _1, _2, _3);
	}

	public static <T0, T1, T2, T3, T4> Tuple5<T0, T1, T2, T3, T4> of(T0 _0, T1 _1, T2 _2, T3 _3, T4 _4) {
		return new Tuple5<>(_0, _1, _2, _3, _4);
	}

}
