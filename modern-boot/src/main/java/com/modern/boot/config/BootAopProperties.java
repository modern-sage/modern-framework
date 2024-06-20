package com.modern.boot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.modern.boot.config.StarterConfigConst.PROPERTIES_PREFIX;

/**
 * AOP配置属性
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 **/
@Data
@ConfigurationProperties(prefix = PROPERTIES_PREFIX + ".aop")
public class BootAopProperties {

    /**
     * 是否启用
     */
    private boolean enabled;

    /**
     * 日志输出类型：print-type
     *   1. 请求和响应分开，按照执行顺序打印
     *   2. ThreadLocal线程绑定，方法执行结束时，连续打印请求和响应日志
     *   3. ThreadLocal线程绑定，方法执行结束时，同时打印请求和响应日志
     */
    int printType = 1;

    /**
     * 请求日志在控制台是否格式化输出，local环境建议开启，服务器环境设置为false
     */
    private boolean requestLogFormat = true;

    /**
     * 响应日志在控制台是否格式化输出，local环境建议开启，服务器环境设置为false
     */
    private boolean responseLogFormat = true;

}
