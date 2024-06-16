package com.modernframework.base.security;


import com.modernframework.base.Identifiable;

/**
 * 授权服务组件
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface IAuthorizationService {

    /**
     * 签发token
     *
     * @param loginObj 被授权对象
     * @return String
     */
    String token(Identifiable<? extends Object> loginObj);

}
