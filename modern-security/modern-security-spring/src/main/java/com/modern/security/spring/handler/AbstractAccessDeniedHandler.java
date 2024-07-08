package com.modern.security.spring.handler;

import com.modern.security.SecurityConstant;
import com.modernframework.base.utils.CompatibleObjectMapper;
import com.modernframework.base.vo.Rs;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AbstractAccessDeniedHandler
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public abstract class AbstractAccessDeniedHandler {

    protected final <T> void writeResponse(HttpServletResponse response, Rs<T> result) throws IOException {
        //处理编码方式，防止中文乱码的情况
        response.setContentType(SecurityConstant.CONTENT_TYPE_JSON);
        //塞到HttpServletResponse中返回给前台
        response.getWriter().write(CompatibleObjectMapper.INSTANCE.writeValueAsString(result));
    }

}
