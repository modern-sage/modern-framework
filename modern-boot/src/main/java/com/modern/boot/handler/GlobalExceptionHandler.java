package com.modern.boot.handler;

import com.modernframework.base.constant.BizOpCode;
import com.modernframework.base.exception.BizException;
import com.modernframework.base.vo.Rs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author jjz
 * @since 0.1.0
 */
@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 非法参数验证异常
     *
     * @param ex MethodArgumentNotValidException
     * @return Rs
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Rs<String> handleMethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        return Rs.fail(message);
    }


    /**
     * 自定义业务/数据异常处理
     *
     * @param exception BusinessException
     * @return Rs<String>
     */
    @ExceptionHandler(value = {BizException.class})
    public Rs<String> businessException(BizException exception) {
        log.info("BusinessException: {}", exception.getMessage(), exception);
        return new Rs<String>()
                .setCode(exception.getCode())
                .setMessage(exception.getMessage());
    }

    /**
     * 参数校验异常
     *
     * @param ex 参数校验异常
     * @return Rs
     */
    @ExceptionHandler(BindException.class)
    public Rs<Object> handleBindException(BindException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        log.error("参数校验异常:{}({})", fieldError.getDefaultMessage(), fieldError.getField());
        return Rs.result(BizOpCode.FAIL, fieldError.getDefaultMessage());
    }


}
