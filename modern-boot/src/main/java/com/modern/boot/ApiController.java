package com.modern.boot;

import com.modernframework.base.constant.BizOpCode;
import com.modernframework.base.vo.Rs;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * RsEST API 公共控制器
 * </p>
 *
 * @since 2018-11-08
 */
@Slf4j
public class ApiController {

    /**
     * <p>
     * 请求成功
     * </p>
     *
     * @param data 数据内容
     * @param <T>  对象泛型
     * @return
     */
    protected <T> Rs<T> ok(T data) {
        return Rs.ok(data);
    }

    /**
     * <p>
     * 请求失败
     * </p>
     *
     * @param msg 提示内容
     * @return
     */
    protected Rs<Object> fail(String msg) {
        return Rs.fail(msg);
    }

    /**
     * <p>
     * 请求失败
     * </p>
     *
     * @param rsCode 请求错误码
     */
    protected Rs<Object> fail(BizOpCode rsCode) {
        return Rs.fail(rsCode);
    }

}
