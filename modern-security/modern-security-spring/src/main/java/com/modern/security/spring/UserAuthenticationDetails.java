package com.modern.security.spring;

import com.modern.security.AuthenticationDetails;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 存储有关身份验证请求的其他详细信息
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@Data
public class UserAuthenticationDetails implements AuthenticationDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
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
