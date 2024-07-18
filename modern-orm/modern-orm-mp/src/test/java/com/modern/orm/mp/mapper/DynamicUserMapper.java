package com.modern.orm.mp.mapper;

import com.modern.orm.mp.DynamicUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DynamicUserMapper extends MdBaseMapper<DynamicUser> {
}
