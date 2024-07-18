package com.modern.orm.mp;


import com.modernframework.orm.anno.DynamicTableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 动态用户表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@DynamicTableName(dynamicExpression = "biz_#{#tenant}_#{#tableName}")
public class DynamicUser extends BaseBizPo<DynamicUser> {

    private String position;
    private String dept;

}
