package com.modern.orm.mp.config;

import com.modernframework.core.utils.ArrayUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Optional;

import static com.modern.orm.mp.config.MdMpConfigContent.PROPERTIES_PREFIX;

/**
 * 表前缀处理器条件
 */
public class TablePrefixCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String prefixs = context.getEnvironment().getProperty(PROPERTIES_PREFIX + ".prefixs");
        String[] ss = Optional.ofNullable(prefixs).map(s -> s.split(",")).orElse(new String[]{});
        return ArrayUtils.isNotEmpty(ss);
    }
}
