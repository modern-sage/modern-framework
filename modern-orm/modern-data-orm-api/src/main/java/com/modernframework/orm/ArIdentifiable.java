package com.modernframework.orm;


import com.modernframework.base.Identifiable;

/**
 * Active Record(活动记录)
 * 特点是一个模型类对应关系型数据库中的一个表，而模型类的一个实例对应表中的一行记录。
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface ArIdentifiable<T extends ArIdentifiable<?>> extends Identifiable<Long> {

    /**
     * 根据自身ID进行更新
     *
     * @return boolean
     */
    boolean updateById();

    /**
     * 根据 ID 查询，套娃实现
     */
    T selectById(Long id);

    /**
     * 实体类型
     */
    default PoType getPoType() {
        return PoType.NORMAL;
    }

}
