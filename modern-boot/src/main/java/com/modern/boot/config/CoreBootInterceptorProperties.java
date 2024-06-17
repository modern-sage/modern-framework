package com.modern.boot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import static com.modern.boot.config.StarterConfigConst.PROPERTIES_PREFIX;


/**
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 0.2.0
 */
@Data
@ConfigurationProperties(prefix = PROPERTIES_PREFIX + ".interceptor")
public class CoreBootInterceptorProperties {

    /**
     * 权限拦截器排除路径
     */
    @NestedConfigurationProperty
    private InterceptorConfig permission = new InterceptorConfig();

    /**
     * 资源拦截器
     */
    @NestedConfigurationProperty
    private InterceptorConfig resource = new InterceptorConfig();

    /**
     * 上传拦截器
     */
    @NestedConfigurationProperty
    private InterceptorConfig upload = new InterceptorConfig();

    /**
     * 下载拦截器
     */
    @NestedConfigurationProperty
    private InterceptorConfig download = new InterceptorConfig();

    @Data
    public static class InterceptorConfig {

        /**
         * 是否启用
         */
        private boolean enabled;

        /**
         * 排除路径
         */
        private String[] excludePaths;

        /**
         * 包含的路径
         */
        private String[] includePaths;

    }

}
