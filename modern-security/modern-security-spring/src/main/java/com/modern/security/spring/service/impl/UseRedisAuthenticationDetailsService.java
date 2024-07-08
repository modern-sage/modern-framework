package com.modern.security.spring.service.impl;

import com.modern.security.AuthenticationDetailsService;
import com.modern.security.spring.UserAuthenticationDetails;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * AuthenticationDetailsService内存实现
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class UseRedisAuthenticationDetailsService implements AuthenticationDetailsService<UserAuthenticationDetails> {

    private final RedisTemplate redisTemplate;

    public final static String TOKEN = "token:";

    public UseRedisAuthenticationDetailsService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 根据访问凭证获取登陆的其他信息
     *
     * @param accessToken 访问凭证
     */
    @Override
    public UserAuthenticationDetails getAuthDetailsByAccessToken(String accessToken) {
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
    public boolean saveAuthDetails(UserAuthenticationDetails authDetails) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(TOKEN + authDetails.getAccessToken(), authDetails,
                authDetails.getAccessExpireTime(), TimeUnit.MILLISECONDS));
    }

    /**
     * 删除
     */
    @Override
    public boolean removeAuthDetails(String accessToken) {
        return redisTemplate.delete(TOKEN + accessToken);
    }

}
