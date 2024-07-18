package com.modern.orm.mp.service.impl;


import com.modern.orm.mp.DynamicUser;
import com.modern.orm.mp.mapper.DynamicUserMapper;
import com.modern.orm.mp.service.AbstractBaseService;
import com.modern.orm.mp.service.DynamicUserService;
import org.springframework.stereotype.Service;

@Service
public class DynamicUserServiceImpl extends AbstractBaseService<DynamicUserMapper, DynamicUser>
        implements DynamicUserService {
}
