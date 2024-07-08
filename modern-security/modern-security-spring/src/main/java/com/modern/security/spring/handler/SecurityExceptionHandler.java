package com.modern.security.spring.handler;

import com.modernframework.base.constant.BizOpCode;
import com.modernframework.base.vo.Rs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义授权异常实现
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@ControllerAdvice
@RestController
@Slf4j
public class SecurityExceptionHandler {

    /**
     * 认证异常
     */
    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.OK)
    public Rs<String> authenticationExceptionHandler() {
        return Rs.result(BizOpCode.UNAUTHORIZED);
    }

    /**
     * 授权异常
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.OK)
    public Rs<String> accessDeniedExceptionHandler() {
        return Rs.result(BizOpCode.FORBIDDEN);
    }
}
