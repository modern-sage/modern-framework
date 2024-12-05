package com.modern.security.perm;

import java.util.List;

/**
 * 授权主体
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public interface PermPrincipal extends Perm {

    /**
     * 授权身份
     */
    List<String> getSubject();

    /**
     * 授权服务
     */
    List<String> getService();

}
