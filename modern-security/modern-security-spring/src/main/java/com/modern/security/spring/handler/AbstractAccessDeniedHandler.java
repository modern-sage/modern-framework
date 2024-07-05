package com.modern.security.spring.handler;

/**
 * AbstractAccessDeniedHandler
 *
 * @author zj
 * @since 0.1.0
 */
public abstract class AbstractAccessDeniedHandler {

//    /**
//     * 成功回写
//     *
//     * @param response HttpServletResponse
//     * @throws IOException
//     */
//    protected final <T> void writeSuccess(HttpServletResponse response, T data) throws IOException {
//        Rs<T> result = Rs.ok(data);
//        writeResponse(response, result);
//    }
//
//    /**
//     * 异常回写
//     *
//     * @param response HttpServletResponse
//     * @throws IOException
//     */
//    protected final void writeError(HttpServletResponse response, String errorMsg) throws IOException {
//        Rs<String> result = Rs.fail(errorMsg);
//        writeResponse(response, result);
//    }
//
//    protected final <T> void writeResponse(HttpServletResponse response, Rs<T> result) throws IOException {
//        //处理编码方式，防止中文乱码的情况
//        response.setContentType(SecurityConstant.CONTENT_TYPE_JSON);
//        //塞到HttpServletResponse中返回给前台
//        response.getWriter().write(CompatibleObjectMapper.INSTANCE.writeValueAsString(result));
//    }


}
