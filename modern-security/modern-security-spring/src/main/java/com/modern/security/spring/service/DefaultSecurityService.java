package com.modern.security.spring.service;

import com.modern.security.*;
import com.modern.security.spring.UserDetailsAdapter;
import com.modern.security.spring.UserAuthenticationDetails;
import com.modern.security.spring.config.SpringSecurityProperties;
import com.modern.security.spring.utils.TokenUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Objects;

/**
 * 授权登录业务实现
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class DefaultSecurityService implements SecurityService {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final SpringSecurityProperties securityProperties;
    private final AuthenticationDetailsService authenticationDetailsService;

    public DefaultSecurityService(UserDetailsService userDetailsService, AuthenticationManager authenticationManager,
                                  SpringSecurityProperties securityProperties, AuthenticationDetailsService authenticationDetailsService) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.securityProperties = securityProperties;
        this.authenticationDetailsService = authenticationDetailsService;
    }

    /**
     * 登录用户
     *
     * @param username 用户名
     * @param password 密码
     * @return UserCertificate
     */
    @Override
    public UserCertificate login(String username, String password) {
        //创建Authentication对象
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        //调用AuthenticationManager的authenticate方法进行认证
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        //登录成功以后用户信息、
        UserDetailsAdapter authUser = (UserDetailsAdapter) authentication.getPrincipal();

        long now = System.currentTimeMillis();
        long accessExpireTime = now + securityProperties.getAccessTokenExpire();
        long refreshExpireTime = now + securityProperties.getRefreshTokenExpire();

        UserCertificate certificate = new UserCertificate();
        certificate.setUserId(authUser.getUserId());
        certificate.setUsername(authUser.getUsername());
        certificate.setToken(TokenUtils.createAccessToken());
        certificate.setRefreshToken(TokenUtils.createAccessToken());

        // 存储登陆的详细信息（Token等）
        UserAuthenticationDetails authDetails = new UserAuthenticationDetails();
        authDetails.setAccessToken(certificate.getToken());
        authDetails.setAccessExpireTime(accessExpireTime);
        authDetails.setRefreshToken(certificate.getRefreshToken());
        authDetails.setRefreshExpireTime(refreshExpireTime);
        authenticationDetailsService.saveAuthDetails(authDetails);

        return certificate;
    }

    /**
     * getUserInfo
     */
    @Override
    public AuthenticationUser getUserInfo() {
        return null;
    }

    /**
     * 刷新token
     *
     * @param accessToken  访问令牌
     * @param refreshToken 刷新令牌
     * @return MdCertificate
     */
    @Override
    public UserCertificate refreshToken(String accessToken, String refreshToken) {
        final AuthenticationDetails authDetailsByAccessToken = authenticationDetailsService.getAuthDetailsByAccessToken(accessToken);
        if(authDetailsByAccessToken == null) {
            throw new AccessDeniedException("无授权");
        }
        if(Objects.equals(authDetailsByAccessToken.getRefreshToken(), refreshToken)) {
            authenticationDetailsService.removeAuthDetails(accessToken);
            throw new AccessDeniedException("刷新Token非法");
        }
        if (System.currentTimeMillis() > authDetailsByAccessToken.getRefreshExpireTime()) {
            authenticationDetailsService.removeAuthDetails(accessToken);
            throw new AccessDeniedException("刷新Token过期");
        }

        // 重新生成token
        final UserDetails authUser = userDetailsService.loadUserByUsername(authDetailsByAccessToken.getUsername());
        if(authUser != null) {
            UserCertificate certificate = new UserCertificate();
            certificate.setUsername(authUser.getUsername());
            certificate.setToken(TokenUtils.createAccessToken());
            certificate.setRefreshToken(TokenUtils.createAccessToken());
            return certificate;
        } else {
            throw new AccessDeniedException("Token非法");
        }
    }

    /**
     * 用户登出
     *
     * @return Boolean
     */
    @Override
    public boolean logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthenticationDetails details = (AuthenticationDetails) authentication.getDetails();
        return authenticationDetailsService.removeAuthDetails(details.getAccessToken());
    }

}