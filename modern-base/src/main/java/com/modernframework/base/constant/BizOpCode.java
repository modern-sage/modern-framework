package com.modernframework.base.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 业务操作编码
 * </p>
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum BizOpCode {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 系统异常
     */
    FAIL(500, "操作失败"),

    /**
     * 认证失败
     */
    UNAUTHORIZED(401, "认证失败"),

    /**
     * 授权失败
     */
    FORBIDDEN(403, "授权失败"),

    ;

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 默认信息
     */
    private final String defaultMsg;

}
