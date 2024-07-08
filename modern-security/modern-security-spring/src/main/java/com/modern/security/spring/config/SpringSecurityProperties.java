package com.modern.security.spring.config;

import com.modern.security.spring.TokenStoragePolicy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.modern.security.spring.TokenStoragePolicy.useMemory;

/**
 * 安全配置
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@Setter
@Getter
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
    private TokenStoragePolicy storagePolicy = useMemory;

    public SpringSecurityProperties() {
    }

}
