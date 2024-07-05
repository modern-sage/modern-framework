package com.modern.security.spring;

import com.modern.security.AuthenticationDetails;


/**
 * 存储有关身份验证请求的其他详细信息
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class UserAuthenticationDetails implements AuthenticationDetails {

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

    public UserAuthenticationDetails() {
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public Long getAccessExpireTime() {
        return accessExpireTime;
    }

    public void setAccessExpireTime(Long accessExpireTime) {
        this.accessExpireTime = accessExpireTime;
    }

    @Override
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public Long getRefreshExpireTime() {
        return refreshExpireTime;
    }

    public void setRefreshExpireTime(Long refreshExpireTime) {
        this.refreshExpireTime = refreshExpireTime;
    }
}
