package com.modern.security.spring.support.controller.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * LoginParam
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginParam implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;

}
