package com.modern.orm.mp.service.impl;


import com.modern.orm.mp.MdDataTestPerson;
import com.modern.orm.mp.mapper.MdDataTestPersonMapper;
import com.modern.orm.mp.service.AbstractBaseService;
import com.modern.orm.mp.service.MdDataTestPersonService;
import org.springframework.stereotype.Service;

@Service
public class MdDataTestPersonServiceImpl extends AbstractBaseService<MdDataTestPersonMapper, MdDataTestPerson>
        implements MdDataTestPersonService {
}
