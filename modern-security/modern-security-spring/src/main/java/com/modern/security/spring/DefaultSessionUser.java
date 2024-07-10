package com.modern.security.spring;

import com.modernframework.base.security.context.SessionUser;
import lombok.Data;

/**
 * DefaultSessionUser
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
@Data
public class DefaultSessionUser implements SessionUser {

    /**
     * 用户Id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 访问凭证Token
     */
    private String accessToken;

    /**
     * 访问凭证过期时间
     */
    private Long accessExpireTime;

    /**
     * 访问凭证刷新Token
     */
    private String refreshToken;

    /**
     * 访问凭证刷新过期时间
     */
    private Long refreshExpireTime;

}
