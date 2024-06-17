package com.modern.orm.mp.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;

import java.util.List;

/**
 * 定制sql注入
 */
public class MdDefaultMpSqlInjector extends DefaultSqlInjector {

    /**
     * 添加两个新的方法
     *
     * @param mapperClass 当前mapper
     * @param tableInfo
     * @return
     */
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        // todo fixme
//        methodList.add(new TenantIgnoreSelectById(null));
//        methodList.add(new TenantIgnoreUpdateById(null));
//        methodList.add(new TenantIgnoreDeleteBatchIds(null));
        return methodList;
    }
}

