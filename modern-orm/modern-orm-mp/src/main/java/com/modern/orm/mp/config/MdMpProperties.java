package com.modern.orm.mp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.modern.orm.mp.config.MdMpConfigContent.PROPERTIES_PREFIX;

/**
 * <pre>
 *  MpProperties
 * </pre>
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = PROPERTIES_PREFIX)
public class MdMpProperties {

    /**
     * 是否开启租户
     */
    boolean enableTenant = false;

    /**
     * 租户表名忽略设置
     */
    private String[] tenantIgnoreTables = new String[]{};

    /**
     * 表名前缀
     */
    private String[] prefixs = new String[]{};

    /**
     * 表名是否驼峰转下划线
     */
    boolean underline = true;
}
