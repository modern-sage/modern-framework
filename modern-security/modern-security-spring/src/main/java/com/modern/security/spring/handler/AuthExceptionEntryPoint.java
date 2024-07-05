package com.modern.security.spring.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

/**
 * 自定义认证异常实现
 *
 * @author zj
 * @since 0.1.0
 */
public class AuthExceptionEntryPoint extends AbstractAccessDeniedHandler implements AuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(AuthExceptionEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        log.info("认证失败: {}", authException.getMessage());
        log.debug("认证失败栈信息", authException);
        throw new AccessDeniedException("认证失败");
    }
}
