package com.modern.security.spring;

import com.fasterxml.jackson.databind.JsonNode;
import com.modern.security.perm.*;
import lombok.Data;

/**
 * 权限策略语句
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
@Data
public class PermPolicyStatement implements PermStatement {

    private String effect;

    /**
     * 操作是指对具体资源的操作。
     */
    private JsonNode action;

    /**
     * 资源是指被授权的具体对象。
     */
    private JsonNode resource;

    /**
     * 条件是指授权生效的条件。
     */
    private JsonNode condition;

    /**
     * 授权主体是指允许或拒绝访问资源的主体，仅适用于基于资源的策略。
     */
    private JsonNode principal;

}
