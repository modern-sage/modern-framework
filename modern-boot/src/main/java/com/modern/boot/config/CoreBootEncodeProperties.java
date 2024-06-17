package com.modern.boot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.StandardCharsets;

import static com.modern.boot.config.StarterConfigConst.PROPERTIES_PREFIX;


/**
 * encoder相关配置
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 0.2.0
 */
@Data
@ConfigurationProperties(prefix = PROPERTIES_PREFIX + ".encoding")
public class CoreBootEncodeProperties {

    private String charset = StandardCharsets.UTF_8.name();

}
