package com.modern.security.spring.support.service;

import com.modern.security.AuthenticationDetailsService;
import com.modern.security.spring.support.entity.SysAuthDetails;
import com.modernframework.base.service.BaseService;

/**
 * SysAuthDetailsService
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public interface SysAuthDetailsService extends BaseService<SysAuthDetails>, AuthenticationDetailsService<SysAuthDetails> {

}
