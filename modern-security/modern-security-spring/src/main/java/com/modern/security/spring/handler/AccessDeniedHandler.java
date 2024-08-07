package com.modern.security.spring.handler;

import com.modernframework.base.constant.BizOpCode;
import com.modernframework.base.vo.Rs;
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
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class AccessDeniedHandler extends AbstractAccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    private static final Logger log = LoggerFactory.getLogger(AccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("授权失败: {}", accessDeniedException.getMessage());
        log.debug("授权失败栈信息", accessDeniedException);
        writeResponse(response, Rs.fail(BizOpCode.FORBIDDEN));
    }
}
