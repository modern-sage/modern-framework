package com.modernframework.base.security;


import com.modernframework.base.Identifiable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 权限获取接口
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface IPermissionService {

    /**
     * 默认所有权限集合
     */
    String ALL_PERMISSIONS = "md-permissions-all";

    /**
     * 根据ID获取权限
     *
     * @param t
     * @return List
     */
    default List<String> permissions(Object t) {
        return new ArrayList<>(Collections.singleton(IPermissionService.ALL_PERMISSIONS));
    }

    /**
     * 比对鉴权对象和序列化ID
     *
     * @param loginObj 鉴权对象
     * @param id       序列化
     * @return boolean
     */
    default boolean owner(Identifiable<? extends Object> loginObj, Serializable id) {
        return loginObj != null && Objects.equals(loginObj.getId(), id);
    }

}
