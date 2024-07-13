package com.modern.orm.mp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.modernframework.orm.ArIdentifiable;
import lombok.Getter;

/**
 * 包含其他不同场景中的模型的业务属性的全属性模型
 *
 * @param <T>
 * @author zhangj
 */
public class IdPo<T extends IdPo<T>> extends Model<T> implements ArIdentifiable<T> {

    /**
     * 主键
     * 采用AUTO类型时会忽略插入Id的值
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Getter
    protected Long id;

    /**
     * tenantId
     */
    @Getter
    protected Long tenantId;


    /**
     * 设置主键
     *
     * @param id 主键值
     * @return <T>
     */
    public T setId(Long id) {
        this.id = id;
        return (T) this;
    }

    public T setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return (T) this;
    }

    @Override
    public boolean updateById() {
        return super.updateById();
    }

    @Override
    public T selectById(Long id) {
        return super.selectById(id);
    }
}
