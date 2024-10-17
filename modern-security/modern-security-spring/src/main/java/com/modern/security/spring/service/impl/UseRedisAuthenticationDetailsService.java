package com.modern.security.spring.service.impl;

import com.modern.security.AuthenticationDetailsService;
import com.modern.security.UserCertificate;
import com.modern.security.spring.UserAuthenticationDetails;
import com.modernframework.core.utils.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * AuthenticationDetailsService内存实现
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class UseRedisAuthenticationDetailsService implements AuthenticationDetailsService<UserAuthenticationDetails> {

    private final RedisTemplate<String, Serializable> redisTemplate;

    public final static String TOKEN = "token:";

    public UseRedisAuthenticationDetailsService(RedisTemplate<String, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 根据访问凭证获取登陆的其他信息
     *
     * @param accessToken 访问凭证
     */
    @Override
    public UserAuthenticationDetails getAuthDetailsByAccessToken(String accessToken) {
        if(StringUtils.isBlank(accessToken)) {
            return null;
        }
        Object object = redisTemplate.opsForValue().get(TOKEN + accessToken);
        if (object == null) {
            return null;
        }
        return (UserAuthenticationDetails) object;
    }

    /**
     * 保存
     */
    @Override
    public boolean saveAuthDetails(UserCertificate certificate,
                                   long accessExpireTime, long refreshExpireTime) {
        UserAuthenticationDetails authDetails = new UserAuthenticationDetails();
        authDetails.setUserId(certificate.getUserId());
        authDetails.setUsername(certificate.getUsername());
        authDetails.setAccessToken(certificate.getToken());
        authDetails.setAccessExpireTime(accessExpireTime);
        authDetails.setRefreshToken(certificate.getRefreshToken());
        authDetails.setRefreshExpireTime(refreshExpireTime);
        redisTemplate.opsForValue().set(TOKEN + authDetails.getAccessToken(), authDetails,
                authDetails.getAccessExpireTime(), TimeUnit.MILLISECONDS);
        return true;
    }

    /**
     * 删除
     */
    @Override
    public boolean removeAuthDetails(String accessToken) {
        return Boolean.TRUE.equals(redisTemplate.delete(TOKEN + accessToken));
    }

}
