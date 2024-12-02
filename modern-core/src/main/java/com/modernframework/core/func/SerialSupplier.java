package com.modernframework.core.func;

import java.io.Serializable;
import java.util.function.Predicate;

/**
 * SerialSupplier <br/>
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @see Predicate
 * @since 1.0.0
 */
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
