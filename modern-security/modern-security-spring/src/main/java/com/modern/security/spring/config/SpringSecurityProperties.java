package com.modern.security.spring.config;

import com.modern.security.spring.StoragePolicy;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.modern.security.spring.StoragePolicy.IN_MEMORY;

/**
 * 安全配置
 *
 * @author zj
 * @since 0.1.0
 */
@ConfigurationProperties(prefix = "modern.security")
public class SpringSecurityProperties {

    /**
     * access token 过期时间，默认1小时
     */
    private long accessTokenExpire = 60 * 60 * 1000L;

    /**
     * refresh token 过期时间，默认24小时
     */
    private long refreshTokenExpire = 24 * 60 * 60 * 1000L;

    /**
     * 认证的token key
     */
    private String accessTokenKey = "AccessToken";

    /**
     * 权限存储策略
     */
    private StoragePolicy storagePolicy = IN_MEMORY;

    public SpringSecurityProperties() {
    }

    public long getAccessTokenExpire() {
        return accessTokenExpire;
    }

    public void setAccessTokenExpire(long accessTokenExpire) {
        this.accessTokenExpire = accessTokenExpire;
    }

    public long getRefreshTokenExpire() {
        return refreshTokenExpire;
    }

    public void setRefreshTokenExpire(long refreshTokenExpire) {
        this.refreshTokenExpire = refreshTokenExpire;
    }

    public String getAccessTokenKey() {
        return accessTokenKey;
    }

    public void setAccessTokenKey(String accessTokenKey) {
        this.accessTokenKey = accessTokenKey;
    }

    public StoragePolicy getStoragePolicy() {
        return storagePolicy;
    }

    public void setStoragePolicy(StoragePolicy storagePolicy) {
        this.storagePolicy = storagePolicy;
    }
}
