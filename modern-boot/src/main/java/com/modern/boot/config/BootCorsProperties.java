package com.modern.boot.config;

import com.modernframework.base.constant.BaseConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.modern.boot.config.StarterConfigConst.PROPERTIES_PREFIX;


/**
 * 跨域属性配置
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 0.2.0
 **/
@Data
@ConfigurationProperties(prefix = PROPERTIES_PREFIX + ".cors")
public class BootCorsProperties {

    /**
     * 是否启用跨域，默认启用
     */
    private boolean enable = true;

    /**
     * CORS过滤的路径，默认：/**
     */
    private String path = "/**";

    /**
     * 允许访问的源
     */
    private List<String> allowedOrigins = Collections.singletonList(CorsConfiguration.ALL);

    /**
     * 允许访问的请求头
     */
    private List<String> allowedHeaders = Collections.singletonList(CorsConfiguration.ALL);

    /**
     * 是否允许发送cookie
     */
    private boolean allowCredentials = true;

    /**
     * 允许访问的请求方式
     */
    private List<String> allowedMethods = Collections.singletonList(CorsConfiguration.ALL);

    /**
     * 允许响应的头
     */
    private List<String> exposedHeaders = Arrays.asList(BaseConstant.TOKEN);

    /**
     * 该响应的有效时间默认为30分钟，在有效时间内，浏览器无须为同一请求再次发起预检请求
     */
    private Long maxAge = 1800L;

}