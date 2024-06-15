package com.modernframework.base;

import java.io.Serializable;

/**
 * 获取ID的接口
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface Identifiable<ID>  extends Serializable {
    /**
     * 获取id
     */
    ID getId();
}
