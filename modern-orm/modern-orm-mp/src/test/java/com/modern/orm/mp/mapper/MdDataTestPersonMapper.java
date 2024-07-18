package com.modern.orm.mp.mapper;

import com.modern.orm.mp.MdDataTestPerson;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MdDataTestPersonMapper extends MdBaseMapper<MdDataTestPerson> {
}
