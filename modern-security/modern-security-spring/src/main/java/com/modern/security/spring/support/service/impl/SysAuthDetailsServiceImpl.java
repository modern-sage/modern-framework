package com.modern.security.spring.support.service.impl;

import com.modern.orm.mp.service.AbstractBaseService;
import com.modern.security.AuthenticationDetails;
import com.modern.security.spring.support.entity.SysAuthDetails;
import com.modern.security.spring.support.mapper.SysAuthDetailsMapper;
import com.modern.security.spring.support.service.SysAuthDetailsService;
import com.modernframework.core.utils.StringUtils;

/**
 * SysAuthDetailsServiceImpl
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class SysAuthDetailsServiceImpl extends AbstractBaseService<SysAuthDetailsMapper, SysAuthDetails>
        implements SysAuthDetailsService {


    /**
     * 根据访问凭证获取登陆的其他信息
     *
     * @param accessToken 访问凭证
     * @return SysAuthDetails
     */
    @Override
    public SysAuthDetails getAuthDetailsByAccessToken(String accessToken) {
        if(StringUtils.isBlank(accessToken)) {
            return null;
        }
        SysAuthDetails authDetails = one(where().eq(AuthenticationDetails::getAccessToken, accessToken));
        if (authDetails == null) {
            return null;
        }
        if (System.currentTimeMillis() > authDetails.getAccessExpireTime()) {
            removeById(authDetails.getId());
            return null;
        }
        return authDetails;
    }

    /**
     * 保存
     *
     * @return boolean
     */
    @Override
    public boolean saveAuthDetails(SysAuthDetails authDetails) {
        return save(authDetails);
    }

    /**
     * 删除
     *
     * @return boolean
     */
    @Override
    public boolean removeAuthDetails(String accessToken) {
        return remove(where().eq(SysAuthDetails::getAccessToken, accessToken));
    }
}
