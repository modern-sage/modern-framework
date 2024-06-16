package com.modernframework.core.func;

import java.io.Serializable;

@FunctionalInterface
public interface SerialSupplier<R> extends Serializable {

	R call() throws Exception;

	default R callWithRuntimeException(){
		try {
			return call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
