package com.modern.boot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import static com.modern.boot.config.StarterConfigConst.PROPERTIES_PREFIX;


/**
 * Filter配置属性
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 **/
@Data
@ConfigurationProperties(prefix = PROPERTIES_PREFIX + ".filter")
public class BootFilterProperties {

    /**
     * 请求路径Filter配置
     */
    @NestedConfigurationProperty
    private FilterConfig requestPath = new FilterConfig();

    @Data
    public static class FilterConfig {

        /**
         * 包含的路径
         */
        private String[] includePaths;

        /**
         * 排除路径
         */
        private String[] excludePaths;

    }
}
