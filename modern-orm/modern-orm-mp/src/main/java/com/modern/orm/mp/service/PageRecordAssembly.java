package com.modern.orm.mp.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modernframework.base.vo.PageRec;

/**
 * 分页组装器
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
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
