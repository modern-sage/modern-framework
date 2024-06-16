package com.modernframework.core.utils.tuple;

import java.util.Objects;

public final class Tuple2<T0, T1> {

	public T0 _0;
	public T1 _1;

	public Tuple2(T0 _0, T1 _1) {
		this._0 = _0;
		this._1 = _1;
	}

	public T0 get_0() {
		return _0;
	}

	public void set_0(T0 _0) {
		this._0 = _0;
	}

	public T1 get_1() {
		return _1;
	}

	public void set_1(T1 _1) {
		this._1 = _1;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (o instanceof Tuple2) {
			@SuppressWarnings("rawtypes")
			Tuple2 t = (Tuple2) o;
			return Objects.equals(t._0, _0) && Objects.equals(t._1, _1);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_0, _1);
	}

	@Override
	public String toString() {
		return "(" + _0 + ", " + _1 + ")";
	}

}
