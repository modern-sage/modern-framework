package com.modern.security.spring.config;

import com.modern.security.AuthenticationDetailsService;
import com.modern.security.SecurityService;
import com.modern.security.spring.TokenStoragePolicy;
import com.modern.security.spring.filter.AuthenticationTokenFilter;
import com.modern.security.spring.handler.AccessDeniedHandler;
import com.modern.security.spring.handler.AuthExceptionEntryPoint;
import com.modern.security.spring.service.DefaultSecurityService;
import com.modern.security.spring.service.SecurityPermissionService;
import com.modern.security.spring.service.impl.UseMemoryAuthenticationDetailsService;
import com.modern.security.spring.service.impl.UseRedisAuthenticationDetailsService;
import com.modern.security.spring.support.service.SysAuthUserService;
import com.modern.security.spring.support.service.impl.SysAuthDetailsServiceImpl;
import com.modern.security.spring.support.service.impl.SysAuthUserServiceImpl;
import com.modern.security.spring.support.service.impl.DefaultUserDetailsServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 装配类
 * 参考  <br/>
 * <ul>
 *     <li>https://www.cnblogs.com/wtzbk/p/17260243.html</li>
 *     <li>https://learn.lianglianglee.com/%e4%b8%93%e6%a0%8f/Spring%20Security%20%e8%af%a6%e8%a7%a3%e4%b8%8e%e5%ae%9e%e6%93%8d/00%20%e5%bc%80%e7%af%87%e8%af%8d%20%20Spring%20Security%ef%bc%8c%e4%b8%ba%e4%bd%a0%e7%9a%84%e5%ba%94%e7%94%a8%e5%ae%89%e5%85%a8%e4%b8%8e%e8%81%8c%e4%b8%9a%e4%b9%8b%e8%b7%af%e4%bf%9d%e9%a9%be%e6%8a%a4%e8%88%aa.md</li>
 * </ul>
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(SpringSecurityProperties.class)
@ComponentScan("com.modern.security.spring")
@MapperScan("com.modern.security.spring.support.mapper")
public class SpringSecurityConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SpringSecurityConfiguration.class);
    @Autowired
    private NoAuthConfiguration noAuthConfiguration;

    @Autowired
    @SuppressWarnings("rawtypes")
    private RedisTemplate redisTemplate;

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public SysAuthUserService sysAuthUserService() {
        return new SysAuthUserServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService(SysAuthUserService sysAuthUserService) {
        return new DefaultUserDetailsServiceImpl(sysAuthUserService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    @SuppressWarnings("rawtypes")
    public AuthenticationDetailsService authenticationDetailsService(SpringSecurityProperties properties) {
        AuthenticationDetailsService authenticationDetailsService = null;
        TokenStoragePolicy tokenStoragePolicy = properties.getStoragePolicy();
        switch (tokenStoragePolicy) {
            case useMemory -> authenticationDetailsService = new UseMemoryAuthenticationDetailsService();
            case useRedis -> authenticationDetailsService = new UseRedisAuthenticationDetailsService(redisTemplate);
            case useDataBase -> authenticationDetailsService = new SysAuthDetailsServiceImpl();
        }
        if (authenticationDetailsService == null) {
            log.error("authDetailsService 适配不成功，properties: {}", properties);
            authenticationDetailsService = new UseMemoryAuthenticationDetailsService();
        }
        return authenticationDetailsService;
    }

    @Bean
    @SuppressWarnings("rawtypes")
    @ConditionalOnMissingBean(SecurityService.class)
    public SecurityService authService(UserDetailsService userDetailsService, AuthenticationManager authenticationManager,
                                       SpringSecurityProperties securityProperties, AuthenticationDetailsService authenticationDetailsService) {
        return new DefaultSecurityService(userDetailsService, authenticationManager,
                securityProperties, authenticationDetailsService);
    }

    /**
     * Token验证过滤器
     */
    @Bean
    @SuppressWarnings("rawtypes")
    public AuthenticationTokenFilter authenticationTokenFilter(AuthenticationDetailsService authenticationDetailsService,
                                                               SpringSecurityProperties properties) {
        return new AuthenticationTokenFilter(authenticationDetailsService, properties.getAccessTokenKey());
    }

    /**
     * 认证资源过滤链配置
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   UserDetailsService userDetailsService, AuthenticationTokenFilter authenticationTokenFilter) throws Exception {
        // 登陆的接口标记不做任何的权限过滤，并且在里面做好登陆的操作
        //登录认证处理
//        http.formLogin()
//                .successHandler(new CustomizeAuthenticationSuccessHandler())
//                .failureHandler(new CustomizeAuthenticationFailureHandler());

        //默认的登出
//        http.logout().permitAll().addLogoutHandler(new CustomizeLogoutHandler())
//                .logoutSuccessHandler(new CustomizeLogoutSuccessHandler())
//                .deleteCookies("JSESSIONID")

        //支持跨域
        http.cors().and()
                //不使用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //csrf关闭
                .and().csrf().disable()
//                .and().csrf().ignoringAntMatchers(noAuthConfiguration.getPermitAllUrls().toArray(new String[0]))
                // 当被 @NoAuth 标注的接口没有任何的认证鉴权限制
                .authorizeRequests(rep -> rep
                        .antMatchers(noAuthConfiguration.getPermitAllUrls().toArray(new String[0])).permitAll()
                        // 剩下的全部页面需要执行认证
                        .anyRequest().authenticated())

                .exceptionHandling()
                //认证异常处理
//                .authenticationEntryPoint(new AuthExceptionEntryPoint())
                //授权异常处理
                .accessDeniedHandler(new AccessDeniedHandler())
                //自定义认证
                .and()
                //token 验证
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService);
        return http.build();
    }

    /**
     * 密码加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 权限服务
     */
    @Bean("perm")
    public SecurityPermissionService permissionService() {
        return new SecurityPermissionService();
    }

}

