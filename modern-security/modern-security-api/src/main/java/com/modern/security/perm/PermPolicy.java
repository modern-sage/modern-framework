package com.modern.security.perm;

import java.util.List;

import static com.modern.security.perm.Perm.ALLOW;
import static com.modern.security.perm.Perm.DENY;

/**
 * 权限策略
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class PermPolicy {

    private String version;

    private List<PermStatement> statement;

    /**
     * 判定结果
     *
     * @return String
     */
    public String judgment() {
        if(statement != null && statement.size() > 0) {
            String result = DENY;
            for (PermStatement statement : statement) {
                if(DENY.equals(statement.getEffect())) {
                    return DENY;
                } else if (ALLOW.equals(statement.getEffect())) {
                    result = ALLOW;
                }
                return result;
            }
        }
        return DENY;
    }

}
