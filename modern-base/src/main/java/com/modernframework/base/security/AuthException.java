package com.modernframework.base.security;


import com.modernframework.base.exception.BizException;

/**
 * AuthException
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class AuthException extends BizException {
    /**
     * @param message 错误提示信息构造方法
     */
    public AuthException(String message) {
        super(message);
    }

    /**
     * @param code    错误提示编码
     * @param message 错误提示信息构造方法
     */
    public AuthException(String code, String message) {
        super(code, message);
    }

}
