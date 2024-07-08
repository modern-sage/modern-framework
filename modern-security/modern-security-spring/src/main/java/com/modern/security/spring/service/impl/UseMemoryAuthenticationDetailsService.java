package com.modern.security.spring.service.impl;

import com.modern.security.AuthenticationDetailsService;
import com.modern.security.spring.UserAuthenticationDetails;
import com.modernframework.core.utils.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AuthenticationDetailsService内存实现
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class UseMemoryAuthenticationDetailsService implements AuthenticationDetailsService<UserAuthenticationDetails> {

    private final Map<String, UserAuthenticationDetails> memoryMap = new ConcurrentHashMap<>();

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

}
