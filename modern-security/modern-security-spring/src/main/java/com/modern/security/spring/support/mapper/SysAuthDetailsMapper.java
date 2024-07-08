package com.modern.security.spring.support.mapper;


import com.modern.orm.mp.mapper.MdBaseMapper;
import com.modern.security.spring.support.entity.SysAuthDetails;
import org.mybatis.spring.annotation.MapperScan;

/**
 * 有关身份验证请求的其他详细信息
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@MapperScan
public interface SysAuthDetailsMapper extends MdBaseMapper<SysAuthDetails> {

}
