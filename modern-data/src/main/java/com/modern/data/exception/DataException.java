package com.modern.data.exception;


/**
 * 数据库层异常
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class DataException extends RuntimeException {

    public DataException(Throwable cause) {
        super(cause);
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(String messageFormat, Object... args) {
        super(String.format(messageFormat, args));
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

}
