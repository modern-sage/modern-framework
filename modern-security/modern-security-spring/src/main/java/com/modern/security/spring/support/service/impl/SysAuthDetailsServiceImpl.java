package com.modern.security.spring.support.service.impl;

import com.modern.orm.mp.service.AbstractBaseService;
import com.modern.security.AuthenticationDetails;
import com.modern.security.UserCertificate;
import com.modern.security.spring.support.entity.SysAuthDetails;
import com.modern.security.spring.support.mapper.SysAuthDetailsMapper;
import com.modern.security.spring.support.service.SysAuthDetailsService;
import com.modernframework.core.utils.StringUtils;

import static com.modernframework.base.BaseConstant.NO;
import static com.modernframework.base.BaseConstant.YES;

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
    public boolean saveAuthDetails(UserCertificate certificate,
                                   long accessExpireTime, long refreshExpireTime) {
        SysAuthDetails sysAuthDetails = new SysAuthDetails();
        sysAuthDetails.setUserId(certificate.getUserId());
        sysAuthDetails.setUsername(certificate.getUsername());
        sysAuthDetails.setAccessToken(certificate.getToken());
        sysAuthDetails.setAccessExpireTime(accessExpireTime);
        sysAuthDetails.setRefreshToken(certificate.getRefreshToken());
        sysAuthDetails.setRefreshExpireTime(refreshExpireTime);
        sysAuthDetails.setPermissions(certificate.getPermissions());
        sysAuthDetails.setIsSuperAdmin(certificate.isSuperAdmin()? YES: NO);
        return save(sysAuthDetails);
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
