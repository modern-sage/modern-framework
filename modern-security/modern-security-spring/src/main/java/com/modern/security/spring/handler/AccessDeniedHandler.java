package com.modern.security.spring.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义授权异常实现
 *
 * @author zj
 * @since 0.1.0
 */
public class AccessDeniedHandler extends AbstractAccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    private static final Logger log = LoggerFactory.getLogger(AccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("授权失败: {}", accessDeniedException.getMessage());
        log.debug("授权失败栈信息", accessDeniedException);
        throw new java.nio.file.AccessDeniedException("无授权");
    }
}
