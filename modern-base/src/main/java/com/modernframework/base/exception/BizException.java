package com.modernframework.base.exception;


import com.modernframework.base.constant.BizOpConstant;
import com.modernframework.core.utils.StringUtils;
import lombok.Getter;

/**
 * 业务异常
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@Getter
public class BizException extends RuntimeException {

    private Integer code;

    public BizException(Throwable cause) {
        super(cause);
        this.code = BizOpConstant.FAIL.getCode();
    }

    public BizException(String message) {
        super(message);
        this.code = BizOpConstant.FAIL.getCode();
    }

    public BizException(String messageFormat, Object... args) {
        super(StringUtils.format(messageFormat, args));
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
