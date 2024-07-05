package com.modern.security.spring.service.impl;

import com.modern.security.AuthenticationDetailsService;
import com.modern.security.spring.UserAuthenticationDetails;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.access.AccessDeniedException;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AuthenticationDetailsService内存实现
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class InMemoryAuthenticationDetailsService implements AuthenticationDetailsService<UserAuthenticationDetails> {

    private final Map<String, UserAuthenticationDetails> memoryMap = new ConcurrentHashMap<>();

    /**
     * 根据访问凭证获取登陆的其他信息
     *
     * @param accessToken 访问凭证
     */
    @Override
    public UserAuthenticationDetails getAuthDetailsByAccessToken(String accessToken) {
        UserAuthenticationDetails authDetails = memoryMap.get(accessToken);
        if (authDetails == null) {
            return null;
        }
        if (System.currentTimeMillis() > authDetails.getAccessExpireTime()) {
            removeAuthDetails(accessToken);
            return null;
        }
        return authDetails;
    }

    /**
     * 保存
     */
    @Override
    public boolean saveAuthDetails(UserAuthenticationDetails authDetails) {
        return memoryMap.put(authDetails.getAccessToken(), authDetails) != null;
    }

    /**
     * 删除
     */
    @Override
    public boolean removeAuthDetails(String accessToken) {
        return memoryMap.remove(accessToken) != null;
    }

    /**
     * 验证 accessToken
     *
     */
    @Override
    public void validateAccessToken(String accessToken) {
        UserAuthenticationDetails authDetails = memoryMap.get(accessToken);
        if(authDetails == null) {
            throw new AccessDeniedException("无凭证");
        }
        if (System.currentTimeMillis() > authDetails.getAccessExpireTime()) {
            throw new AccessDeniedException("凭证已过期");
        }
    }

    /**
     * 验证 refreshToken
     *
     */
    @Override
    public void validateRefreshToken(String accessToken, String refreshToken) {
        UserAuthenticationDetails authDetails = memoryMap.get(accessToken);
        if(authDetails == null) {
            throw new AccessDeniedException("无凭证");
        }
        if (System.currentTimeMillis() > authDetails.getAccessExpireTime()) {
            throw new AccessDeniedException("凭证已过期");
        }
        if (!Objects.equals(refreshToken, authDetails.getRefreshToken())) {
            throw new AccessDeniedException("刷新凭证不匹配");
        }
    }
}
