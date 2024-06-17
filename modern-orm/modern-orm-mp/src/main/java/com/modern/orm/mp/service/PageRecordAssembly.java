package com.modern.orm.mp.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modernframework.base.PageRec;

/**
 * 分页组装器
 *
 * @author zj
 */
public class PageRecordAssembly<T> {

    private Page<T> page;

    public PageRecordAssembly(Page<T> page) {
        this.page = page;
    }

    public PageRec<T> assemblyPageRecord() {
        return new PageRec<>((int) page.getCurrent(), (int) page.getPages(), page.getRecords(), (int) page.getTotal());
    }


}
