package com.modernframework.base.vo;

import com.modernframework.base.constant.BizOpCode;
import com.modernframework.base.utils.CompatibleObjectMapper;
import com.modernframework.core.lang.Asserts;
import com.modernframework.core.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * Response Result
 * </p>
 *
 * @author zj
 * @since 0.1.0
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Rs<T> implements Serializable {

    /**
     * 响应类型
     */
    private String type;

    /**
     * 响应状态编码
     */
    private Integer code;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T result;

    /**
     * 响应时间
     */
    private LocalDateTime time = LocalDateTime.now();

    public static <T> Rs<T> result(boolean success) {
        if (success) {
            return ok();
        }
        return fail();
    }

    public static <T> Rs<T> result(BizOpCode rsCode) {
        return result(rsCode, null);
    }

    public static <T> Rs<T> result(BizOpCode rsCode, T data) {
        return result(rsCode, null, data);
    }

    public static <T> Rs<T> result(BizOpCode rsCode, String msg, T data) {
        boolean success = Objects.equals(rsCode, BizOpCode.SUCCESS);
        String message = rsCode.getDefaultMsg();
        if (StringUtils.isNotBlank(msg)) {
            message = msg;
        }
        Rs<T> re = new Rs<>();
        re.setCode(rsCode.getCode())
                .setMessage(message)
                .setResult(data)
                .setSuccess(success)
                .setTime(LocalDateTime.now());

        return re;
    }

    public static <T> Rs<T> ok() {
        return ok(null);
    }

    public static <T> Rs<T> ok(T data) {
        return result(BizOpCode.SUCCESS, data);
    }

    public static <T> Rs<T> ok(T data, String msg) {
        return result(BizOpCode.SUCCESS, msg, data);
    }

    public static Rs<Map<String, Object>> okMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return ok(map);
    }

    public static <T> Rs<T> fail(BizOpCode rsCode) {
        return result(rsCode, null);
    }

    public static <T> Rs<T> fail(String msg) {
        return result(BizOpCode.FAIL, msg, null);
    }

    public static Rs<String> fail(Throwable e) {
        Rs<String> re = new Rs<>();
        re.setCode(BizOpCode.FAIL.getCode())
                .setMessage(e.getMessage())
                .setResult(ExceptionUtils.getStackTrace(e))
                .setSuccess(false)
                .setTime(LocalDateTime.now());
        return re;
    }

    public static <T> Rs<T> fail(BizOpCode rsCode, String msg, T data) {
        Asserts.notEquals(rsCode, BizOpCode.SUCCESS);
        return result(rsCode, msg, data);

    }

    public static <T> Rs<T> fail(BizOpCode rsCode, String msg) {
        Asserts.notEquals(rsCode, BizOpCode.SUCCESS);
        return result(rsCode, msg, null);
    }

    public static Rs<Map<String, Object>> fail(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return result(BizOpCode.FAIL, map);
    }

    public static <T> Rs<T> fail() {
        return fail(BizOpCode.FAIL);
    }

    @SneakyThrows
    public String toJsonString() {
        return CompatibleObjectMapper.INSTANCE.writeValueAsString(this);
    }

}
